package com.des.harmony.model

import com.des.harmony.model.enums.Accidental
import com.des.harmony.model.enums.PitchClass
import com.des.harmony.model.enums.Scale
import org.junit.Assert.assertEquals
import org.junit.Test

class NotesSolverTest {

    @Test
    fun major_C() {
        // Given
        val testCase = TestCase(
            scale = Scale.MAYOR,
            pitchClass = PitchClass.C,
            accidental = Accidental.NATURAL,
            expectedNotes = listOf("C", null, "D", null, "E", "F", null, "G", null, "A", null, "B"),
        )
        executeTest(testCase)
    }

    @Test
    fun dominant_C() {
        // Given
        val testCase = TestCase(
            scale = Scale.DOMINANT,
            pitchClass = PitchClass.C,
            accidental = Accidental.NATURAL,
            expectedNotes = listOf("C", null, "D", null, "E", "F", null, "G", null, "A", "B♭", null),
        )
        executeTest(testCase)
    }

    @Test
    fun minor_C() {
        // Given
        val testCase = TestCase(
            scale = Scale.MINOR,
            pitchClass = PitchClass.C,
            accidental = Accidental.NATURAL,
            expectedNotes = listOf("C", null, "D", "E♭", null, "F", null, "G", "A♭", null, "B♭", null),
        )
        executeTest(testCase)
    }

    @Test
    fun chromatic_C() {
        // Given
        val testCase = TestCase(
            scale = Scale.CHROMATIC,
            pitchClass = PitchClass.C,
            accidental = Accidental.NATURAL,
            expectedNotes = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"),
        )
        executeTest(testCase)
    }

    @Test
    fun major_C_Sharp() {
        // Given
        val testCase = TestCase(
            scale = Scale.MAYOR,
            pitchClass = PitchClass.CS_DF,
            accidental = Accidental.SHARP,
            expectedNotes = listOf("C#", null, "D#", null, "E#", "F#", null, "G#", null, "A#", null, "B#"),
        )
        executeTest(testCase)
    }

    @Test
    fun minor_C_Sharp() {
        // Given
        val testCase = TestCase(
            scale = Scale.MINOR,
            pitchClass = PitchClass.CS_DF,
            accidental = Accidental.SHARP,
            expectedNotes = listOf("C#", null, "D#", "E", null, "F#", null, "G#", "A", null, "B", null),
        )
        executeTest(testCase)
    }

    @Test
    fun chromatic_C_Sharp() {
        // Given
        val testCase = TestCase(
            scale = Scale.CHROMATIC,
            pitchClass = PitchClass.C,
            accidental = Accidental.SHARP,
            expectedNotes = listOf("C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C"),
        )
        executeTest(testCase)
    }

    @Test
    fun major_D_Flat() {
        // Given
        val testCase = TestCase(
            scale = Scale.MAYOR,
            pitchClass = PitchClass.CS_DF,
            accidental = Accidental.FLAT,
            expectedNotes = listOf("D♭", null, "E♭", null, "F", "G♭", null, "A♭", null, "B♭", null, "C"),
        )
        executeTest(testCase)
    }

    @Test
    fun dominant_D_Flat() {
        // Given
        val testCase = TestCase(
            scale = Scale.DOMINANT,
            pitchClass = PitchClass.CS_DF,
            accidental = Accidental.FLAT,
            expectedNotes = listOf("D♭", null, "E♭", null, "F", "G♭", null, "A♭", null, "B♭", "C♭", null),
        )
        executeTest(testCase)
    }

    @Test
    fun minor_D_Flat() {
        // Given
        val testCase = TestCase(
            scale = Scale.MINOR,
            pitchClass = PitchClass.CS_DF,
            accidental = Accidental.FLAT,
            expectedNotes = listOf("D♭", null, "E♭", "F♭", null, "G♭", null, "A♭", "B♭♭", null, "C♭", null),
        )
        executeTest(testCase)
    }

    @Test
    fun chromatic_D_Flat() {
        // Given
        val testCase = TestCase(
            scale = Scale.CHROMATIC,
            pitchClass = PitchClass.D,
            accidental = Accidental.FLAT,
            expectedNotes = listOf("D♭", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A", "B♭", "B", "C"),
        )
        executeTest(testCase)
    }

    @Test
    fun major_D() {
        // Given
        val testCase = TestCase(
            scale = Scale.MAYOR,
            pitchClass = PitchClass.D,
            accidental = Accidental.NATURAL,
            expectedNotes = listOf("D", null, "E", null, "F#", "G", null, "A", null, "B", null, "C#"),
        )
        executeTest(testCase)
    }

    @Test
    fun minor_D() {
        // Given
        val testCase = TestCase(
            scale = Scale.MINOR,
            pitchClass = PitchClass.D,
            accidental = Accidental.NATURAL,
            expectedNotes = listOf("D", null, "E", "F", null, "G", null, "A", "B♭", null, "C", null),
        )
        executeTest(testCase)
    }

    @Test
    fun major_D_Sharp() {
        // Given
        val testCase = TestCase(
            scale = Scale.MAYOR,
            pitchClass = PitchClass.D,
            accidental = Accidental.SHARP,
            expectedNotes = listOf("D#", null, "E#", null, "F##", "G#", null, "A#", null, "B#", null, "C##"),
        )
        executeTest(testCase)
    }

    @Test
    fun major_F() {
        // Given
        val testCase = TestCase(
            scale = Scale.MAYOR,
            pitchClass = PitchClass.F,
            accidental = Accidental.NATURAL,
            expectedNotes = listOf("F", null, "G", null, "A", "B♭", null, "C", null, "D", null, "E"),
        )
        executeTest(testCase)
    }

    @Test
    fun dominant_F() {
        // Given
        val testCase = TestCase(
            scale = Scale.DOMINANT,
            pitchClass = PitchClass.F,
            accidental = Accidental.NATURAL,
            expectedNotes = listOf("F", null, "G", null, "A", "B♭", null, "C", null, "D", "E♭", null),
        )
        executeTest(testCase)
    }

    @Test
    fun minor_F() {
        // Given
        val testCase = TestCase(
            scale = Scale.MINOR,
            pitchClass = PitchClass.F,
            accidental = Accidental.NATURAL,
            expectedNotes = listOf("F", null, "G", "A♭", null, "B♭", null, "C", "D♭", null, "E♭", null),
        )
        executeTest(testCase)
    }

    @Test
    fun major_B_Flat() {
        val testCase = TestCase(
            scale = Scale.MAYOR,
            pitchClass = PitchClass.B,
            accidental = Accidental.FLAT,
            expectedNotes = listOf("B♭", null, "C", null, "D", "E♭", null, "F", null, "G", null, "A"),
        )
        executeTest(testCase)
    }

    @Test
    fun dominant_B_Flat() {
        val testCase = TestCase(
            scale = Scale.DOMINANT,
            pitchClass = PitchClass.B,
            accidental = Accidental.FLAT,
            expectedNotes = listOf("B♭", null, "C", null, "D", "E♭", null, "F", null, "G", "A♭", null),
        )
        executeTest(testCase)
    }

    @Test
    fun chromatic_B_Flat() {
        // Given
        val testCase = TestCase(
            scale = Scale.CHROMATIC,
            pitchClass = PitchClass.B,
            accidental = Accidental.FLAT,
            expectedNotes = listOf("B♭", "B", "C", "D♭", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A"),
        )
        executeTest(testCase)
    }

    @Test
    fun major_B() {
        // Given
        val testCase = TestCase(
            scale = Scale.MAYOR,
            pitchClass = PitchClass.B,
            accidental = Accidental.NATURAL,
            expectedNotes = listOf("B", null, "C#", null, "D#", "E", null, "F#", null, "G#", null, "A#"),
        )
        executeTest(testCase)
    }

    @Test
    fun chromatic_B() {
        // Given
        val testCase = TestCase(
            scale = Scale.CHROMATIC,
            pitchClass = PitchClass.B,
            accidental = Accidental.NATURAL,
            expectedNotes = listOf("B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#"),
        )
        executeTest(testCase)
    }

    data class TestCase(
        val scale: Scale,
        val pitchClass: PitchClass,
        val accidental: Accidental,
        val expectedNotes: List<String?>,
    )


    private fun executeTest(testCase: TestCase) {
        // When
        val notes = NotesSolver.notesFor(
            selectedScale = testCase.scale,
            selectedPitchClass = testCase.pitchClass,
            selectedAccidental = testCase.accidental,
        )

        // Then
        assertEquals(testCase.expectedNotes, notes)
    }


}

