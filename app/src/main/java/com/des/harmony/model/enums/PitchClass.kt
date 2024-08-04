package com.des.harmony.model.enums

/**
 * Represent each of the twelve semitone pitch classes: C; C#/D♭; D; D#/E♭; etc.
 */
enum class PitchClass(
    val isNatural: Boolean,
    val getNaturalPitchClass: (Accidental) -> NaturalPitchClass,
) {
    C(isNatural = true, getNaturalPitchClass = { NaturalPitchClass.C }),
    CS_DF(isNatural = false, getNaturalPitchClass = {
        when (it) {
            Accidental.SHARP -> NaturalPitchClass.C
            Accidental.FLAT -> NaturalPitchClass.D
            else -> TODO()
        }
    }),
    D(isNatural = true, getNaturalPitchClass = { NaturalPitchClass.D }),
    DS_EF(isNatural = false, getNaturalPitchClass = {
        when (it) {
            Accidental.SHARP -> NaturalPitchClass.D
            Accidental.FLAT -> NaturalPitchClass.E
            else -> TODO()
        }
    }),
    E(isNatural = true, getNaturalPitchClass = { NaturalPitchClass.E }),
    F(isNatural = true, getNaturalPitchClass =  { NaturalPitchClass.F }),
    FS_GF(isNatural = false, getNaturalPitchClass = {
        when (it) {
            Accidental.SHARP -> NaturalPitchClass.F
            Accidental.FLAT -> NaturalPitchClass.G
            else -> TODO()
        }
    }),
    G(isNatural = true, getNaturalPitchClass = { NaturalPitchClass.G }),
    GS_AF(isNatural = false, getNaturalPitchClass = {
        when (it) {
            Accidental.SHARP -> NaturalPitchClass.G
            Accidental.FLAT -> NaturalPitchClass.A
            else -> TODO()
        }
    }),
    A(isNatural = true, getNaturalPitchClass = { NaturalPitchClass.A }),
    AS_BF(isNatural = false, getNaturalPitchClass = {
        when (it) {
            Accidental.SHARP -> NaturalPitchClass.A
            Accidental.FLAT -> NaturalPitchClass.B
            else -> TODO()
        }
    }),
    B(isNatural = true, getNaturalPitchClass = { NaturalPitchClass.B })
}