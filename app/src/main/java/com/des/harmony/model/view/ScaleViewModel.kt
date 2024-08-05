package com.des.harmony.model.view

import androidx.lifecycle.ViewModel
import com.des.harmony.model.NotesSolver.notesFor
import com.des.harmony.model.SHOW_NOTES_WITH_FLAT
import com.des.harmony.model.SHOW_NOTES_WITH_SHARP
import com.des.harmony.model.enums.Accidental
import com.des.harmony.model.enums.PitchClass
import com.des.harmony.model.enums.Scale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScaleViewModel : ViewModel() {

    // Scale UI state
    private val _uiState = MutableStateFlow(ScaleShowState())
    val uiState: StateFlow<ScaleShowState> = _uiState.asStateFlow()

    val scalesToShowOnSelector
        get() = Scale.entries.map { it.scaleName }

    val selectedScaleIndex
        get() = Scale.entries.indexOf(_uiState.value.scale)

    val selectedPitchIndex
        get() = PitchClass.entries.indexOf(_uiState.value.pitch)

    val selectorNotes
        get() = _uiState.value.notesToShowOnSelector

    fun selectScale(index: Int) {
        val selectedScale = Scale.entries[index]
        val selectorNotes = when(selectedScale) {
            Scale.MAYOR,
            Scale.DOMINANT -> SHOW_NOTES_WITH_FLAT
            else -> SHOW_NOTES_WITH_SHARP
        }
        val selectedAccidental = accidentalForPitchAndScale(_uiState.value.pitch, selectedScale)

        _uiState.update { currentState ->
            currentState.copy(
                scale = selectedScale,
                notesToShowOnSelector = selectorNotes,
                accidental = selectedAccidental,
            )
        }
    }

    fun selectPitch(index: Int) {

        val selectedPitchClass = PitchClass.entries[index]
        val selectedAccidental = accidentalForPitchAndScale(selectedPitchClass, _uiState.value.scale)

        _uiState.update { currentState ->
            currentState.copy(
                accidental = selectedAccidental,
                pitch = selectedPitchClass,
            )
        }

    }

    val notesToShow
        get() : List<String?>  = notesFor (
            selectedScale = _uiState.value.scale,
            selectedPitchClass = _uiState.value.pitch,
            selectedAccidental = _uiState.value.accidental,
        )


    private fun accidentalForPitchAndScale(
        selectedPitchClass: PitchClass,
        selectedScale: Scale
    ) = if (selectedPitchClass.isNatural) {
        Accidental.NATURAL
    } else {
        when (selectedScale) {
            Scale.MAYOR,
            Scale.DOMINANT -> Accidental.FLAT

            else -> Accidental.SHARP
        }
    }
}