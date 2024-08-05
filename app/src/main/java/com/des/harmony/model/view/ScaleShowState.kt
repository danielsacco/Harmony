package com.des.harmony.model.view

import com.des.harmony.model.SHOW_NOTES_WITH_FLAT
import com.des.harmony.model.enums.Accidental
import com.des.harmony.model.enums.PitchClass
import com.des.harmony.model.enums.Scale

data class ScaleShowState(
    val scale: Scale = Scale.MAYOR,
    val pitch: PitchClass = PitchClass.C,
    val accidental: Accidental = Accidental.NATURAL,
    val notesToShowOnSelector: List<String> = SHOW_NOTES_WITH_FLAT,
)