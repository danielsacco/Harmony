package com.des.harmony.model

typealias Mode = List<String?>

val MODE_I_IONIAN: Mode = listOf("C", null, "D", null, "E", "F", null, "G", null, "A", null, "B")
val MODE_II_DORIAN: Mode = MODE_I_IONIAN.rotateLeft(2)
val MODE_III_PHRYGIAN: Mode = MODE_II_DORIAN.rotateLeft(2)
val MODE_IV_LYDIAN: Mode = MODE_III_PHRYGIAN.rotateLeft(1)
val MODE_V_MIXOLYDIAN: Mode = MODE_IV_LYDIAN.rotateLeft(2)
val MODE_VI_AEOLIAN: Mode = MODE_V_MIXOLYDIAN.rotateLeft(2)
val MODE_VII_LOCRIAN: Mode = MODE_VI_AEOLIAN.rotateLeft(2)

val SHOW_NOTES_WITH_SHARP = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
val SHOW_NOTES_WITH_FLAT = listOf("C", "D♭", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A", "B♭", "B")


typealias ScaleNotes = List<Boolean>

val SCALE_MAJOR: ScaleNotes = listOf(true, false, true, false, true, true, false, true, false, true, false, true)
val SCALE_DOMINANT: ScaleNotes = listOf(true, false, true, false, true, true, false, true, false, true, true, false)
val SCALE_MINOR: ScaleNotes = listOf(true, false, true, true, false, true, false, true, true, false, true, false)


internal fun <T> Iterable<T>.rotateLeft(distance: Int) = drop(distance) + take(distance)


