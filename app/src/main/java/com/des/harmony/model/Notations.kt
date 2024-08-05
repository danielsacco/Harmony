package com.des.harmony.model

import com.des.harmony.model.enums.Notation

data class Notations (
    val preferred: Notation,
    val alternative: Notation,
)
infix fun Notation.or(that: Notation): Notations = Notations(this, that)
