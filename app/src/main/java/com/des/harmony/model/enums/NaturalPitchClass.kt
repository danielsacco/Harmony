package com.des.harmony.model.enums

import com.des.harmony.model.MODE_III_PHRYGIAN
import com.des.harmony.model.MODE_II_DORIAN
import com.des.harmony.model.MODE_IV_LYDIAN
import com.des.harmony.model.MODE_I_IONIAN
import com.des.harmony.model.MODE_VII_LOCRIAN
import com.des.harmony.model.MODE_VI_AEOLIAN
import com.des.harmony.model.MODE_V_MIXOLYDIAN
import com.des.harmony.model.Mode

/**
 * Represent each of the seven natural pitch classes: C; D; E; etc.
 */
enum class NaturalPitchClass(
    val naturalMode: Mode
) {
    C(MODE_I_IONIAN),
    D(MODE_II_DORIAN),
    E(MODE_III_PHRYGIAN),
    F(MODE_IV_LYDIAN),
    G(MODE_V_MIXOLYDIAN),
    A(MODE_VI_AEOLIAN),
    B(MODE_VII_LOCRIAN)
}