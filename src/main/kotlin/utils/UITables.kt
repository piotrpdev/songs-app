package utils

import com.jakewharton.picnic.TextAlignment
import com.jakewharton.picnic.TextBorder
import com.jakewharton.picnic.renderText
import com.jakewharton.picnic.table
import models.Artist
import models.Song

// DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(date)

/**
 * An object containing preconfigured tables for displaying user interface elements related to songs.
 */
object UITables {
    /**
     * The song menu table, displayed as a formatted string.
     */
    val songMenu = table {
        cellStyle {
            alignment = TextAlignment.MiddleRight
            paddingLeft = 1
            paddingRight = 1
            borderLeft = true
            borderRight = true
        }
        header {
            row {
                cell("Song Menu") {
                    columnSpan = 2
                    alignment = TextAlignment.MiddleCenter
                    border = true
                }
            }
        }
        body {
            row {
                cell("1")
                cell("Add Song")
            }
            row {
                cell("2")
                cell("View Song")
            }
            row {
                cell("3")
                cell("Update Song")
            }
            row {
                cell("4")
                cell("Delete Song")
            }
            row {
                cell("5")
                cell("Explicitify Song")
            }
            row {
                cell("")
                cell("")
            }
            row {
                cell("6")
                cell("Search Songs")
            }
            row {
                cell("7")
                cell("Remove Multiple Songs")
            }
            row {
                cell("")
                cell("")
            }
            row {
                cell("8")
                cell("List Songs")
            }
            row {
                cell("")
                cell("")
            }
            row {
                cell("9")
                cell("Load Songs from File")
            }
            row {
                cell("10")
                cell("Save Songs to File")
                cellStyle {
                    borderBottom = true
                }
            }
        }
        footer {
            row {
                cell("0")
                cell("Exit")
                cellStyle {
                    borderBottom = true
                }
            }
        }
    }.renderText(border = TextBorder.ROUNDED)

    /**
     * The artist menu table, displayed as a formatted string.
     */
    val artistMenu = table {
        cellStyle {
            alignment = TextAlignment.MiddleRight
            paddingLeft = 1
            paddingRight = 1
            borderLeft = true
            borderRight = true
        }
        header {
            row {
                cell("Artist Menu") {
                    columnSpan = 2
                    alignment = TextAlignment.MiddleCenter
                    border = true
                }
            }
        }
        body {
            row {
                cell("1")
                cell("Add Artist")
            }
            row {
                cell("2")
                cell("View Artist")
            }
            row {
                cell("3")
                cell("Update Artist")
            }
            row {
                cell("4")
                cell("Delete Artist")
            }
            row {
                cell("")
                cell("")
            }
            row {
                cell("5")
                cell("Search Artists")
            }
            row {
                cell("")
                cell("")
            }
            row {
                cell("6")
                cell("List Artists")
            }
            row {
                cell("")
                cell("")
            }
            row {
                cell("7")
                cell("Load Artists from File")
            }
            row {
                cell("8")
                cell("Save Artists to File")
                cellStyle {
                    borderBottom = true
                }
            }
        }
        footer {
            row {
                cell("0")
                cell("Exit")
                cellStyle {
                    borderBottom = true
                }
            }
        }
    }.renderText(border = TextBorder.ROUNDED)

    /**
     * The main menu table, displayed as a formatted string.
     */
    val mainMenu = table {
        cellStyle {
            alignment = TextAlignment.MiddleRight
            paddingLeft = 1
            paddingRight = 1
            borderLeft = true
            borderRight = true
        }
        header {
            row {
                cell("Main Menu") {
                    columnSpan = 2
                    alignment = TextAlignment.MiddleCenter
                    border = true
                }
            }
        }
        body {
            row {
                cell("1")
                cell("Songs Menu")
            }
            row {
                cell("2")
                cell("Artists Menu")
                cellStyle {
                    borderBottom = true
                }
            }
        }
        footer {
            row {
                cell("0")
                cell("Exit")
                cellStyle {
                    borderBottom = true
                }
            }
        }
    }.renderText(border = TextBorder.ROUNDED)

    /**
     * The list songs menu table, displayed as a formatted string.
     */
    val listSongsMenu = table {
        cellStyle {
            alignment = TextAlignment.MiddleRight
            paddingLeft = 1
            paddingRight = 1
            borderLeft = true
            borderRight = true
        }
        header {
            row {
                cell("List Songs Menu") {
                    columnSpan = 2
                    alignment = TextAlignment.MiddleCenter
                    border = true
                }
            }
        }
        body {
            row {
                cell("1")
                cell("List All Songs")
            }
            row {
                cell("2")
                cell("List Safe Songs")
            }
            row {
                cell("3")
                cell("List Explicit Songs")
            }
            row {
                cell("4")
                cell("List Songs by Rating")
            }
            row {
                cell("5")
                cell("List Stale Songs")
            }
            row {
                cell("6")
                cell("List Important Songs")
                cellStyle {
                    borderBottom = true
                }
            }
        }
        footer {
            row {
                cell("0")
                cell("Exit")
                cellStyle {
                    borderBottom = true
                }
            }
        }
    }.renderText(border = TextBorder.ROUNDED)

    /**
     * Generates a table containing song information, using a predefined template.
     *
     * @param title The title to display in the table.
     * @param data The list of songs to display in the table.
     * @param allSongs A flag indicating whether to display all songs (default is false).
     * @return A table containing the song information.
     */
    @JvmStatic
    fun songInfoTemplate(title: String, data: List<Song>, allSongs: Boolean) = table {
        cellStyle {
            alignment = TextAlignment.MiddleRight
            paddingLeft = 1
            paddingRight = 1
            borderLeft = true
            borderRight = true
        }
        header {
            row {
                cell(title) {
                    columnSpan = if (allSongs) 5 else 4
                    alignment = TextAlignment.MiddleCenter
                    border = true
                }
            }
            row {
                cellStyle {
                    border = true
                    alignment = TextAlignment.BottomLeft
                }
                if (allSongs) {
                    cell("Index") {
                        alignment = TextAlignment.MiddleCenter
                    }
                }
                cell("Title") {
                    alignment = TextAlignment.MiddleCenter
                }
                cell("Rating") {
                    alignment = TextAlignment.MiddleCenter
                }
                cell("Genre") {
                    alignment = TextAlignment.MiddleCenter
                }
                cell("Explicit") {
                    alignment = TextAlignment.MiddleCenter
                }
                cell("Updated At") {
                    alignment = TextAlignment.MiddleCenter
                }
                cell("Created At") {
                    alignment = TextAlignment.MiddleCenter
                }
            }
        }
        body {
            data.forEachIndexed { index, it ->
                row {
                    if (allSongs) {
                        cell(index.toString()) {
                            alignment = TextAlignment.MiddleCenter
                        }
                    }
                    cell(it.songTitle) {}
                    cell(it.songRating.toString()) {}
                    cell(it.songGenre) {}
                    cell(if (it.isSongExplicit) "Yes" else "No") {}
                    cell(it.updatedAt.toString()) {}
                    cell(it.createdAt.toString()) {}
                    if (index == data.size - 1) {
                        cellStyle {
                            borderBottom = true
                        }
                    }
                }
            }
        }
    }

    /**
     * Generates a table containing song information, using a predefined template.
     *
     * @param title The title to display in the table.
     * @param data The list of songs to display in the table.
     * @param allArtists A flag indicating whether to display all songs (default is false).
     * @return A table containing the song information.
     */
    @JvmStatic
    fun artistInfoTemplate(title: String, data: List<Artist>, allArtists: Boolean) = table {
        cellStyle {
            alignment = TextAlignment.MiddleRight
            paddingLeft = 1
            paddingRight = 1
            borderLeft = true
            borderRight = true
        }
        header {
            row {
                cell(title) {
                    columnSpan = if (allArtists) 4 else 3
                    alignment = TextAlignment.MiddleCenter
                    border = true
                }
            }
            row {
                cellStyle {
                    border = true
                    alignment = TextAlignment.BottomLeft
                }
                if (allArtists) {
                    cell("Index") {
                        alignment = TextAlignment.MiddleCenter
                    }
                }
                cell("Name") {
                    alignment = TextAlignment.MiddleCenter
                }
                cell("Founded Date") {
                    alignment = TextAlignment.MiddleCenter
                }
                cell("Genres") {
                    alignment = TextAlignment.MiddleCenter
                }
            }
        }
        body {
            data.forEachIndexed { index, it ->
                row {
                    if (allArtists) {
                        cell(index.toString()) {
                            alignment = TextAlignment.MiddleCenter
                        }
                    }
                    cell(it.name) {}
                    cell(it.foundedDate.toString()) {}
                    cell(it.genres.joinToString()) {}
                    if (index == data.size - 1) {
                        cellStyle {
                            borderBottom = true
                        }
                    }
                }
            }
        }
    }
}
