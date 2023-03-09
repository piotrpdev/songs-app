package controllers

import models.Note
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NoteAPITest {

    private var learnKotlin: Note? = null
    private var summerHoliday: Note? = null
    private var codeApp: Note? = null
    private var testApp: Note? = null
    private var swim: Note? = null
    private var populatedNotes: NoteAPI? = NoteAPI()
    private var emptyNotes: NoteAPI? = NoteAPI()

    @BeforeEach
    fun setup(){
        learnKotlin = Note("Learning Kotlin", 5, "College", false)
        summerHoliday = Note("Summer Holiday to France", 1, "Holiday", false)
        codeApp = Note("Code App", 4, "Work", false)
        testApp = Note("Test App", 4, "Work", false)
        swim = Note("Swim - Pool", 3, "Hobby", false)

        //adding 5 Note to the notes api
        populatedNotes!!.add(learnKotlin!!)
        populatedNotes!!.add(summerHoliday!!)
        populatedNotes!!.add(codeApp!!)
        populatedNotes!!.add(testApp!!)
        populatedNotes!!.add(swim!!)
    }

    @AfterEach
    fun tearDown(){
        learnKotlin = null
        summerHoliday = null
        codeApp = null
        testApp = null
        swim = null
        populatedNotes = null
        emptyNotes = null
    }

    @Test
    fun `adding a Note to a populated list adds to ArrayList`(){
        val newNote = Note("Study Lambdas", 1, "College", false)
        assertEquals(5, populatedNotes!!.numberOfNotes())
        assertTrue(populatedNotes!!.add(newNote))
        assertEquals(6, populatedNotes!!.numberOfNotes())
        assertEquals(newNote, populatedNotes!!.findNote(populatedNotes!!.numberOfNotes() - 1))
    }

    @Test
    fun `adding a Note to an empty list adds to ArrayList`(){
        val newNote = Note("Study Lambdas", 1, "College", false)
        assertEquals(0, emptyNotes!!.numberOfNotes())
        assertTrue(emptyNotes!!.add(newNote))
        assertEquals(1, emptyNotes!!.numberOfNotes())
        assertEquals(newNote, emptyNotes!!.findNote(emptyNotes!!.numberOfNotes() - 1))
    }

    @Test
    fun `listAllNotes returns No Notes Stored message when ArrayList is empty`() {
        assertEquals(0, emptyNotes!!.numberOfNotes())
        assertTrue(emptyNotes!!.listAllNotes().lowercase().contains("no notes"))
    }

    @Test
    fun `listAllNotes returns Notes when ArrayList has notes stored`() {
        assertEquals(5, populatedNotes!!.numberOfNotes())
        val notesString = populatedNotes!!.listAllNotes().lowercase()
        assertTrue(notesString.contains("learning kotlin"))
        assertTrue(notesString.contains("code app"))
        assertTrue(notesString.contains("test app"))
        assertTrue(notesString.contains("swim"))
        assertTrue(notesString.contains("summer holiday"))
    }

    @Test
    fun `listActiveNotes returns No Active Notes Stored message when ArrayList is empty`() {
        assertEquals(0, emptyNotes!!.numberOfNotes())
        assertTrue(emptyNotes!!.listActiveNotes().lowercase().contains("no active notes"))
    }

    @Test
    fun `listActiveNotes returns No Active Notes Stored message when ArrayList has no active notes stored`() {
        assertEquals(5, populatedNotes!!.numberOfNotes())
        populatedNotes!!.findNote(0)!!.isNoteArchived = true
        populatedNotes!!.findNote(1)!!.isNoteArchived = true
        populatedNotes!!.findNote(2)!!.isNoteArchived = true
        populatedNotes!!.findNote(3)!!.isNoteArchived = true
        populatedNotes!!.findNote(4)!!.isNoteArchived = true
        assertTrue(populatedNotes!!.listActiveNotes().lowercase().contains("no active notes"))
    }

    @Test
    fun `listActiveNotes returns Active Notes when ArrayList has active notes stored`() {
        assertEquals(5, populatedNotes!!.numberOfNotes())
        val notesString = populatedNotes!!.listActiveNotes().lowercase()
        assertTrue(notesString.contains("learning kotlin"))
        assertTrue(notesString.contains("code app"))
        assertTrue(notesString.contains("test app"))
        assertTrue(notesString.contains("swim"))
        assertTrue(notesString.contains("summer holiday"))
    }

    @Test
    fun `listArchivedNotes returns No Archived Notes Stored message when ArrayList is empty`() {
        assertEquals(0, emptyNotes!!.numberOfNotes())
        assertTrue(emptyNotes!!.listArchivedNotes().lowercase().contains("no archived notes"))
    }

    @Test
    fun `listArchivedNotes returns No Archived Notes Stored message when ArrayList has no archived notes stored`() {
        assertEquals(5, populatedNotes!!.numberOfNotes())
        assertTrue(populatedNotes!!.listArchivedNotes().lowercase().contains("no archived notes"))
    }

    @Test
    fun `listArchivedNotes returns Archived Notes when ArrayList has archived notes stored`() {
        assertEquals(5, populatedNotes!!.numberOfNotes())
        populatedNotes!!.findNote(0)!!.isNoteArchived = true
        populatedNotes!!.findNote(1)!!.isNoteArchived = true
        populatedNotes!!.findNote(2)!!.isNoteArchived = true
        populatedNotes!!.findNote(3)!!.isNoteArchived = true
        populatedNotes!!.findNote(4)!!.isNoteArchived = true
        val notesString = populatedNotes!!.listArchivedNotes().lowercase()
        assertTrue(notesString.contains("learning kotlin"))
        assertTrue(notesString.contains("code app"))
        assertTrue(notesString.contains("test app"))
        assertTrue(notesString.contains("swim"))
        assertTrue(notesString.contains("summer holiday"))
    }

    // Test for findNoteByPriority
    @Test
    fun `listNotesBySelectedPriority returns No Notes with Priority Stored message when ArrayList is empty`() {
        assertEquals(0, emptyNotes!!.numberOfNotes())
        assertTrue(emptyNotes!!.listNotesBySelectedPriority(1).lowercase().contains("no notes with priority"))
    }

    @Test
    fun `listNotesBySelectedPriority returns No Notes with Priority Stored message when ArrayList has no notes with priority stored`() {
        assertEquals(5, populatedNotes!!.numberOfNotes())
        assertTrue(populatedNotes!!.listNotesBySelectedPriority(2).lowercase().contains("no notes with priority"))
    }

    @Test
    fun `listNotesBySelectedPriority returns Notes with Priority when ArrayList has notes with priority stored`() {
        assertEquals(5, populatedNotes!!.numberOfNotes())
        val notesString = populatedNotes!!.listNotesBySelectedPriority(4).lowercase()
        assertTrue(notesString.contains("code app"))
        assertTrue(notesString.contains("test app"))
    }

    @Nested
    inner class DeleteNotes {

        @Test
        fun `deleting a Note that does not exist, returns null`() {
            assertNull(emptyNotes!!.deleteNote(0))
            assertNull(populatedNotes!!.deleteNote(-1))
            assertNull(populatedNotes!!.deleteNote(5))
        }

        @Test
        fun `deleting a note that exists delete and returns deleted object`() {
            assertEquals(5, populatedNotes!!.numberOfNotes())
            assertEquals(swim, populatedNotes!!.deleteNote(4))
            assertEquals(4, populatedNotes!!.numberOfNotes())
            assertEquals(learnKotlin, populatedNotes!!.deleteNote(0))
            assertEquals(3, populatedNotes!!.numberOfNotes())
        }
    }

}