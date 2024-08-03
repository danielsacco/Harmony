package com.des.harmony.model.enums

import com.des.harmony.model.Notations
import com.des.harmony.model.or


enum class PitchClass(
    val notationForScale: (Scale) -> Notations
) {
    C({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }}),
    CS_DF({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }}),
    D({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }}),
    DS_EF({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }}),
    E({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }}),
    F({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }}),
    FS_GF({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }}),
    G({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }}),
    GS_AF({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }}),
    A({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }}),
    AS_BF({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }}),
    B({when(it) {
        Scale.CHROMATIC -> Notation.SHARP or Notation.FLAT
        Scale.MAYOR -> Notation.FLAT or Notation.SHARP
        Scale.MINOR -> Notation.SHARP or Notation.FLAT
    }})
}