package com.des.harmony.model.enums

enum class Accidental(val semitonesOffset: Int, val symbol: String) {
    DOUBLE_FLAT(-2, "♭♭"),
    FLAT(-1, "♭"),
    NATURAL(0, ""),
    SHARP(1, "#"),
    DOUBLE_SHARP(2, "##");

    companion object {
        fun fromOffset(offset: Int): Accidental = Accidental.entries
                .firstOrNull { it.semitonesOffset == offset }
                ?: NATURAL
    }
}