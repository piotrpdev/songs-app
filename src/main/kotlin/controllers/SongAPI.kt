package controllers

import com.jakewharton.picnic.Table
import com.jakewharton.picnic.TextBorder
import com.jakewharton.picnic.renderText
import models.Song
import persistence.Serializer
import utils.SerializerUtils
import utils.UITables
import java.time.LocalDateTime

/**
 * A class representing a Song API that allows for managing songs using a provided serializer.
 *
 * @param serializerType The serializer used for storing and retrieving songs.
 */
class SongAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var songs = ArrayList<Song>()

    /**
     * Adds a song to the list of songs.
     *
     * @param song The song to add.
     * @return True if the song was added successfully, false otherwise.
     */
    fun add(song: Song): Boolean = songs.add(song)

    /**
     * Deletes a song from the list of songs by index.
     *
     * @param indexToDelete The index of the song to delete.
     * @return The deleted song if the index was valid, null otherwise.
     */
    fun deleteSong(indexToDelete: Int): Song? =
        if (isValidListIndex(indexToDelete, songs)) songs.removeAt(indexToDelete) else null

    /**
     * Removes multiple songs from the list of songs.
     *
     * @param songList The list of songs to remove.
     */
    fun removeMultipleSongs(songList: List<Song>) = songs.removeAll(songList.toSet())

    /**
     * Updates a song at a given index with new song data.
     *
     * @param indexToUpdate The index of the song to update.
     * @param song The new song data.
     * @return True if the song was updated successfully, false otherwise.
     */
    fun updateSong(indexToUpdate: Int, song: Song): Boolean = findSong(indexToUpdate)?.apply {
        songTitle = song.songTitle
        songRating = song.songRating
        songGenre = song.songGenre
        isSongExplicit = song.isSongExplicit
        updatedAt = LocalDateTime.now()
    } != null

    /**
     * Explicitifies a song at a given index.
     *
     * @param indexToUpdate The index of the song to explicitify.
     * @return True if the song was explicit successfully, false otherwise.
     */
    fun explicitifySong(indexToUpdate: Int): Boolean = findSong(indexToUpdate)?.apply {
        isSongExplicit = true
        updatedAt = LocalDateTime.now()
    } != null

    /**
     * Retrieves all songs as a mutable list.
     *
     * @return A mutable list containing all songs.
     */
    fun findAll(): MutableList<Song> = songs.toMutableList()

    /**
     * Lists all songs in a formatted string.
     *
     * @return A formatted string with all songs or a message indicating no songs stored.
     */
    fun listAllSongs(): String = if (songs.isEmpty()) "No songs stored" else
        generateAllSongsTable().renderText(border = TextBorder.ROUNDED)

    /**
     * Lists all safe (non-explicit) songs in a formatted string.
     *
     * @return A formatted string with all safe songs or a message indicating no safe songs stored.
     */
    fun listSafeSongs(): String = if (songs.isEmpty() || numberOfSafeSongs() == 0) "No safe songs stored"
    else
        generateMultipleSongsTable(songs.filter { song -> !song.isSongExplicit }).renderText(border = TextBorder.ROUNDED)

    /**
     * Lists all explicit songs in a formatted string.
     *
     * @return A formatted string with all explicit songs or a message indicating no explicit songs stored.
     */
    fun listExplicitSongs(): String = if (songs.isEmpty() || numberOfExplicitSongs() == 0) "No explicit songs stored"
    else
        generateMultipleSongsTable(songs.filter { song -> song.isSongExplicit }).renderText(border = TextBorder.ROUNDED)

    /**
     * Lists all songs with a given rating in a formatted string.
     *
     * @param rating The rating to filter songs by.
     * @return A formatted string with all songs of the selected rating or a message indicating no songs with the specified rating.
     */
    fun listSongsBySelectedRating(rating: Int): String =
        if (songs.isEmpty() || numberOfSongsByRating(rating) == 0) "No songs with rating"
        else
            generateMultipleSongsTable(songs.filter { song -> song.songRating == rating }).renderText(border = TextBorder.ROUNDED)

    /**
     * Lists all songs that haven't been updated in a given number of days.
     *
     * @param days The number of days to filter songs by.
     * @return A formatted string with all stale songs or a message indicating no stale songs stored.
     */
    fun listStaleSongs(days: Int): String = if (songs.isEmpty() || numberOfStaleSongs(days) == 0) "No stale songs stored"
    else
        generateMultipleSongsTable(songs.filter { song -> song.updatedAt.isBefore(LocalDateTime.now().minusDays(days.toLong())) }.sortedBy { it.updatedAt }).renderText(border = TextBorder.ROUNDED)

    /**
     * Lists songs with a rating of 5.
     *
     * @return A formatted string with all important songs or a message indicating no important songs stored.
     */
    fun listImportantSongs(): String = if (songs.isEmpty() || numberOfImportantSongs() == 0) "No important songs stored"
    else
        generateMultipleSongsTable(songs.filter { song -> song.songRating == 5 }).renderText(border = TextBorder.ROUNDED)

    /**
     * Retrieves the number of explicit songs.
     *
     * @return The number of explicit songs.
     */
    fun numberOfExplicitSongs(): Int = songs.count { it.isSongExplicit }

    /**
     * Retrieves the number of safe (non-explicit) songs.
     *
     * @return The number of safe songs.
     */
    fun numberOfSafeSongs(): Int = songs.count { !it.isSongExplicit }

    /**
     * Retrieves the number of songs with a given rating.
     *
     * @param rating The rating to filter songs by.
     * @return The number of songs with the specified rating.
     */
    fun numberOfSongsByRating(rating: Int): Int = songs.count { it.songRating == rating }

    /**
     * Retrieves the number of songs that haven't been updated in a given number of days.
     *
     * @param days The number of days to filter songs by.
     * @return The number of stale songs.
     */
    fun numberOfStaleSongs(days: Int): Int = songs.count { it.updatedAt.isBefore(LocalDateTime.now().minusDays(days.toLong())) }

    /**
     * Retrieves the number of songs with a rating of 1.
     *
     * @return The number of important songs.
     */
    fun numberOfImportantSongs(): Int = songs.count { it.songRating == 1 }

    /**
     * Retrieves the total number of songs.
     *
     * @return The number of songs.
     */
    fun numberOfSongs(): Int = songs.size

    /**
     * Retrieves a song at a given index.
     *
     * @param index The index of the song to retrieve.
     * @return The song at the given index if the index is valid, null otherwise.
     */
    fun findSong(index: Int): Song? = if (isValidListIndex(index, songs))
        songs[index]
    else null

    /**
     * Finds a song in the list of songs.
     *
     * @param song The song to search for.
     * @return The song if found, null otherwise.
     */
    fun findUsingSong(song: Song): Song? = songs.find { it == song }

    /**
     * Finds the index of a song in the list of songs.
     *
     * @param song The song to search for.
     * @return The index of the song if found, -1 otherwise.
     */
    fun findIndexUsingSong(song: Song): Int = songs.indexOf(song)

    /**
     * Determines if a given index is valid in a list.
     *
     * @param index The index to validate.
     * @param list The list in which to check the index.
     * @return True if the index is valid, false otherwise.
     */
    private fun isValidListIndex(index: Int, list: List<Any>): Boolean = (index >= 0 && index < list.size)

    /**
     * Determines if a given index is valid in the list of songs.
     *
     * @param index The index to validate.
     * @return True if the index is valid, false otherwise.
     */
    fun isValidIndex(index: Int): Boolean = isValidListIndex(index, songs)

    fun isValidIndex(index: String?): Boolean = !index.isNullOrBlank() && index.toIntOrNull() != null && isValidIndex(index.toInt())

    /**
     * Searches for songs with a title containing a given search string.
     *
     * @param searchString The string to search for in song titles.
     * @return A formatted string with matching songs and their indices or an empty string if no matches are found.
     */
    fun searchByTitle(searchString: String) =
        songs.filter { song -> song.songTitle.contains(searchString, ignoreCase = true) }
            .joinToString(separator = "\n") { song -> songs.indexOf(song).toString() + ": " + song.toString() }

    /**
     * Generates a table containing song information, using a predefined template.
     *
     * @param title The title to display in the table.
     * @param data The list of songs to display in the table.
     * @param allSongs An optional parameter to indicate whether to display all songs (default is false).
     * @return A table containing the song information.
     */
    private fun songInfoTemplate(title: String, data: List<Song>, allSongs: Boolean = false): Table =
        UITables.songInfoTemplate(title, data, allSongs)

    /**
     * Generates a table containing a single song's information.
     *
     * @param song The song to display.
     * @return A table containing the song's information.
     */
    fun generateSongTable(song: Song): Table = songInfoTemplate("Song Information", listOf(song))

    /**
     * Generates a table containing multiple songs' information.
     *
     * @param songs The list of songs to display.
     * @return A table containing the songs' information.
     */
    fun generateMultipleSongsTable(songs: List<Song>): Table = songInfoTemplate("Multiple Song Information", songs)

    /**
     * Generates a table containing all songs' information.
     *
     * @return A table containing all songs' information.
     */
    fun generateAllSongsTable(): Table = songInfoTemplate("All Song Information", songs, true)

    /**
     * Seeds the songs with a predefined set of songs.
     */
    fun seedSongs() {
        songs = SerializerUtils.getSeededSongs()
    }

    /**
     * Loads the songs from the serializer.
     *
     * @return True if the songs were loaded successfully, false otherwise.
     * @throws Exception if an error occurs while loading the songs.
     */
    @Throws(Exception::class)
    fun loadSongs(): Boolean =
        serializer.readSongs()?.also {
            songs = it
        } != null

    /**
     * Stores the songs using the serializer.
     *
     * @throws Exception if an error occurs while storing the songs.
     */
    @Throws(Exception::class)
    fun storeSongs() = serializer.writeSongs(songs)
}
