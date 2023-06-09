package utils

import java.time.LocalDateTime
import java.time.format.DateTimeParseException
import kotlin.reflect.KClass

object ValidatorUtils {
    @JvmStatic
    fun stringIsValid(string: String?): Boolean = !string.isNullOrBlank()

    @JvmStatic
    fun intIsValid(int: String?): Boolean = stringIsValid(int) && int!!.toIntOrNull() != null

    @JvmStatic
    fun songRatingIsValid(rating: String?): Boolean = intIsValid(rating) && rating!!.toInt() in 1..5

    @JvmStatic
    fun artistGenresIsValid(genres: String?): Boolean = stringIsValid(genres) && genres!!.split(",").all { it.isNotBlank() }

    @JvmStatic
    fun yesNoIsValid(isExplicit: String?): Boolean = stringIsValid(isExplicit) && isExplicit!!.toCharArray()[0].lowercase() in arrayOf("y", "n")

    @JvmStatic
    fun localDateTimeIsValid(localDateTime: String?): Boolean = stringIsValid(localDateTime) && isValidLocalDateTime(localDateTime!!)

    @JvmStatic
    fun staleDaysIsValid(staleDays: String?): Boolean = intIsValid(staleDays) && staleDays!!.toInt() >= 0

    // https://stackoverflow.com/a/33968683/19020549
    @JvmStatic
    fun isValidLocalDateTime(inDate: String): Boolean {
        try {
            LocalDateTime.parse(inDate)
        } catch (pe: DateTimeParseException) {
            return false
        }
        return true
    }

    // private fun formatPropertyName(propertyName: String): String = propertyName.split("(?=\\p{Upper})").joinToString(" ").lowercase()

    @JvmStatic
    private fun def(prop: Any?) = if (prop != null) " ($prop)" else ""

    @JvmStatic
    fun propertyNameToPrompt(propertyName: String, oldPropertyValue: Any?): String {
        return when (propertyName) {
            "songTitle" -> "Enter song title${def(oldPropertyValue)}: "
            "songRating" -> "Enter song rating (1-low, 2, 3, 4, 5-high)${def(oldPropertyValue)}: "
            "songGenre" -> "Enter song genre${def(oldPropertyValue)}: "
            "isSongExplicit" -> "Enter song explicit status (y/n)${def(oldPropertyValue)}: "
            "artistName" -> "Enter artist name${def(oldPropertyValue)}: "
            "artistFoundedDate" -> "Enter artist founded date (e.g. 2023-03-09T11:30:00)${def(oldPropertyValue)}: "
            "artistGenres" -> "Enter artist genres (e.g. 'rock, pop, rap')${def(oldPropertyValue)}: "
            "updatedAt" -> "Enter song updated at (e.g. 2023-03-09T11:30:00)${def(oldPropertyValue)}: "
            "createdAt" -> "Enter song created at (e.g. 2023-03-09T11:30:00)${def(oldPropertyValue)}: "
            "staleDays" -> "Show songs that haven't been updated in this many days: "
            "songIndex" -> "Enter song index: "
            "artistIndex" -> "Enter artist index: "
            else -> throw IllegalArgumentException("Invalid property name: $propertyName")
        }
    }

    @JvmStatic
    fun propertyNameToError(propertyName: String): String {
        return when (propertyName) {
            "songTitle" -> "Error: song title was invalid. Please enter a string"
            "songRating" -> "Error: song rating was invalid. Please enter an integer between 1 and 5"
            "songGenre" -> "Error: song genre was invalid. Please enter a string"
            "isSongExplicit" -> "Error: song explicit status was invalid. Please enter either 'y' or 'n'"
            "artistName" -> "Error: artist name was invalid. Please enter a string"
            "artistFoundedDate" -> "Error: artist founded date was invalid. Please enter a valid date and time (e.g. 2023-03-09T11:30:00)"
            "artistGenres" -> "Error: artist genres was invalid. Please enter a comma-separated list of strings"
            "updatedAt" -> "Error: song updated at was invalid. Please enter a valid date and time (e.g. 2023-03-09T11:30:00)"
            "createdAt" -> "Error: song created at was invalid. Please enter a valid date and time (e.g. 2023-03-09T11:30:00)"
            "staleDays" -> "Error: invalid number of days. Please enter a valid positive integer."
            "songIndex" -> "Error: invalid song index. Please enter a valid positive integer."
            "artistIndex" -> "Error: invalid artist index. Please enter a valid positive integer."
            "yesNo" -> "Error: invalid input. Please enter either 'y' or 'n'."
            else -> throw IllegalArgumentException("Invalid property name: $propertyName")
        }
    }

    @JvmStatic
    fun propertyNameToValidator(propertyName: String): (String?) -> Boolean {
        return when (propertyName) {
            "songTitle" -> ::stringIsValid
            "songRating" -> ::songRatingIsValid
            "songGenre" -> ::stringIsValid
            "isSongExplicit" -> ::yesNoIsValid
            "artistName" -> ::stringIsValid
            "artistFoundedDate" -> ::localDateTimeIsValid
            "artistGenres" -> ::artistGenresIsValid
            "updatedAt" -> ::localDateTimeIsValid
            "createdAt" -> ::localDateTimeIsValid
            "staleDays" -> ::staleDaysIsValid
            "yesNo" -> ::yesNoIsValid
            else -> throw IllegalArgumentException("Invalid property name: $propertyName")
        }
    }

    fun <T : Any> stringToGeneric(string: String?, type: KClass<T>): T {
        return when (type) {
            String::class -> string as T
            Int::class -> string!!.toInt() as T
            Boolean::class -> (string!!.toCharArray()[0].lowercase() == "y") as T
            LocalDateTime::class -> LocalDateTime.parse(string) as T
            List::class -> string!!.split(",").map { it.trim().lowercase().capitalize() } as T
            else -> throw IllegalArgumentException("Invalid property type: ${type::class}")
        }
    }

    inline fun <reified T : Any> getValidPropertyValue(
        propertyName: String,
        oldPropertyValue: T? = null,
        noinline customValidator: ((String?) -> Boolean)? = null,
        customPrompt: String? = null,
        customError: String? = null
    ): T {
        val isValid = customValidator ?: propertyNameToValidator(propertyName)

        // https://stackoverflow.com/a/3752693/19020549
        print(customPrompt ?: propertyNameToPrompt(propertyName, oldPropertyValue))

        var input: String? = readlnOrNull()

        while (!isValid(input)) {
            input = if (oldPropertyValue != null) {
                oldPropertyValue.toString()
            } else {
                println(customError ?: propertyNameToError(propertyName))
                readlnOrNull()
            }
        }

        return stringToGeneric(input, T::class)
    }
}
