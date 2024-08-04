package com.des.harmony.model

import com.des.harmony.model.enums.Accidental
import com.des.harmony.model.enums.NaturalPitchClass
import com.des.harmony.model.enums.PitchClass
import com.des.harmony.model.enums.Scale
import java.util.Collections

object NotesSolver {

    fun notesFor(
        selectedScale: Scale,
        selectedPitchClass: PitchClass,
        selectedAccidental: Accidental,
    ): List<String?> {

        return when(selectedScale) {
            Scale.MAYOR -> notesForScale(selectedPitchClass, selectedAccidental, SCALE_MAJOR)
            Scale.MINOR -> notesForScale(selectedPitchClass, selectedAccidental, SCALE_MINOR)
            Scale.DOMINANT -> notesForScale(selectedPitchClass, selectedAccidental, SCALE_DOMINANT)
            Scale.CHROMATIC -> chromaticNotesFor(selectedPitchClass, selectedAccidental)
        }

    }

    private fun chromaticNotesFor(
        pitchClass: PitchClass,
        accidental: Accidental
    ): List<String?> {

        // Which of the seven natural notes is the root
        val tonicNaturalNote: NaturalPitchClass = pitchClass.getNaturalPitchClass(accidental)

        val accidentalOffset = accidental.semitonesOffset

        val notes = tonicNaturalNote.naturalMode.toMutableList()
        if(accidentalOffset != 0) Collections.rotate(notes, -accidentalOffset)

        notes.forEachIndexed { index, note ->
            if(note == null) {
                if(accidental != Accidental.FLAT) {
                    val previousPosition = if(index == 0) 11 else index - 1
                    notes[index] = "${notes[previousPosition]}#"
                } else {
                    val nextPosition = if(index == 11) 0 else index + 1
                    notes[index] = "${notes[nextPosition]}♭"
                }
            }
        }

        return notes
    }

    private fun notesForScale(
        pitchClass: PitchClass,
        accidental: Accidental,
        scale: ScaleNotes,
    ): List<String?> {

        // Which of the seven natural notes is the root
        val tonicNaturalNote: NaturalPitchClass = pitchClass.getNaturalPitchClass(accidental)

        val naturalMode: Mode = tonicNaturalNote.naturalMode

        val scalePositions = scale.mapIndexed { index, fillThisPosition ->
            if(fillThisPosition) index else null
        }.filterNotNull()

        val noteNamesList = naturalMode.filterNotNull()
        val notes = MutableList<String?>(12) {null}
        val accidentalOffset = accidental.semitonesOffset

        noteNamesList.forEachIndexed { index, noteName ->
            val positionInScale = scalePositions[index]
            val positionInMode = naturalMode.indexOf(noteName)
            val thisNoteOffset = positionInScale - positionInMode + accidentalOffset
            val accidentalSymbolToAppend = Accidental.fromOffset(thisNoteOffset).symbol

            notes[positionInScale] = "$noteName$accidentalSymbolToAppend"
        }

        return notes
    }
}
