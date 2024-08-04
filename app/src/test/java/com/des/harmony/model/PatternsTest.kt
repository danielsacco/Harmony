package com.des.harmony.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PatternsTest {

    @Test
    fun modesAreCorrectlyCreated() {

        assertEquals(
            listOf("C", null, "D", null, "E", "F", null, "G", null, "A", null, "B"),
            MODE_I_IONIAN
        )

        assertEquals(
            listOf("D", null, "E", "F", null, "G", null, "A", null, "B", "C", null),
            MODE_II_DORIAN
        )

        assertEquals(
            listOf("E", "F", null, "G", null, "A", null, "B", "C", null, "D", null),
            MODE_III_PHRYGIAN
        )

        assertEquals(
            listOf("F", null, "G", null, "A", null, "B", "C", null, "D", null, "E"),
            MODE_IV_LYDIAN
        )

        assertEquals(
            listOf("G", null, "A", null, "B", "C", null, "D", null, "E", "F", null),
            MODE_V_MIXOLYDIAN
        )

        assertEquals(
            listOf("A", null, "B", "C", null, "D", null, "E", "F", null, "G", null),
            MODE_VI_AEOLIAN
        )

        assertEquals(
            listOf("B", "C", null, "D", null, "E", "F", null, "G", null, "A", null),
            MODE_VII_LOCRIAN
        )

    }
}