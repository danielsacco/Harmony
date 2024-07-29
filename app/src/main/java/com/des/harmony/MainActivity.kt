package com.des.harmony

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.des.harmony.ui.theme.HarmonyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HarmonyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SampleCircles() {
// Creating a Column Layout
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Pruebitas
        HarmonicWheel(
            tonic = Tonic.C,
            scale = Scale.MINOR,
        )
    }
}

@Composable
fun HarmonicWheel(
    tonic: Tonic = Tonic.C,
    scale: Scale = Scale.MAYOR,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            //.fillMaxSize()
            .fillMaxWidth()
            .height(500.dp)
    ) {
        Text(
            text = "${tonic.representation} ${scale.scaleName}",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(20.dp))

        Selector()

        Spacer(modifier = Modifier.size(20.dp))

        val textMeasurer = rememberTextMeasurer()
        val semitonesInScale = scale.intervals.map { it.semitones }
        val notes = getRepresentation(scale, tonic).notes

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val circlesDifferenceInPx = 25.dp.toPx()

            semitonesInScale.forEach {
                // Draw lines
                withTransform({
                    rotate(DELTA_ANGLE * it)
                }) {
                    line(center, center.copy(y = circlesDifferenceInPx))
                }

                val note = notes[it + tonic.offsetFromC]
                val noteSize = textMeasurer.measure(note).size

                val xPos = size.center.x + size.minDimension * xFactor[it]
                val yPos = size.center.y + size.minDimension * yFactor[it]

                val position = Offset(
                    x = xPos - (noteSize.width/2) - circlesDifferenceInPx * xFactor[it],
                    y = yPos - (noteSize.height/2) - circlesDifferenceInPx * yFactor[it],
                )

                drawText(
                    textMeasurer = textMeasurer,
                    text = note,
                    topLeft = position,
                    style = TextStyle.Default.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            drawCircle(
                color = Color(0xff0f9d58),
                center = center,
                radius = size.minDimension / 2,
                style = Stroke(5F)
            )

            drawCircle(
                color = Color(0xff0f9d58),
                center = center,
                radius = (size.minDimension / 2) - circlesDifferenceInPx,
                style = Stroke(5F)
            )
        }



    }
}

@Composable
fun Selector(
    onSelectScale: () -> Unit = {},
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {}) {
                Text(text = "Major")
            }
            Button(onClick = {}) {
                Text(text = "Minor")
            }
            Button(onClick = {}) {
                Text(text = "Chromatic")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Representation.SHARP.notes.forEachIndexed { index, note ->
                Button(onClick = {}) {
                    Text(text = note)
                }
            }



        }
    }
}


private fun DrawScope.line(
    start: Offset,
    end: Offset
) {
    drawLine(
        color = Color(0xff0f9d58),
        start = start,
        end = end,
        strokeWidth = 5F,
    )
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun HarmonicWheelPreview() {
    HarmonyTheme {
        SampleCircles()
    }
}


const val SEMITONES = 12
const val DELTA_ANGLE = 360F / SEMITONES

// X and Y offset for each semitone positioning
val xFactor = arrayOf(0f, 0.25f, 0.433f, 0.5f, 0.433f, 0.25f, 0f, - 0.25f, -0.433f, -0.5f, -0.433f, -0.25f)
val yFactor = arrayOf(-0.5f, -0.433f, -0.25f, 0f, 0.25f, 0.433f, 0.5f, 0.433f, 0.25f, 0f, -0.25f, -0.433f)

enum class Tonic(val offsetFromC: Int, val representation: String) {
    C(0, "C"),
    C_SH(1, "C#"),
    D_B(1, "D♭"),
    D(2, "D"),
    D_SH(3, "D#"),
    E_B(3, "E♭"),
    E(4, "E"),
    F(5, "F"),
    F_SH(6, "F#"),
    G_B(6, "G♭"),
    G(7, "G"),
    G_SH(8, "G#"),
    A_B(8, "A♭"),
    A(9, "A"),
    A_SH(10, "A#"),
    B_B(10, "B♭"),
    B(11, "B")
}

enum class Scale(val scaleName: String, val intervals: Array<Grade>) {
    MAYOR("Major", arrayOf(
        Grade.TONIC,
        Grade.MAYOR_2ND,
        Grade.MAYOR_3ND,
        Grade.JUST_4TH,
        Grade.JUST_5TH,
        Grade.MAYOR_6TH,
        Grade.MAYOR_7TH
    )),
    MINOR("Minor", arrayOf(
        Grade.TONIC,
        Grade.MAYOR_2ND,
        Grade.MINOR_3ND,
        Grade.JUST_4TH,
        Grade.JUST_5TH,
        Grade.MINOR_6TH,
        Grade.MINOR_7TH
    )),
    CHROMATIC("Chromatic", arrayOf(
        Grade.TONIC,
        Grade.MINOR_2ND,
        Grade.MAYOR_2ND,
        Grade.MINOR_3ND,
        Grade.MAYOR_3ND,
        Grade.JUST_4TH,
        Grade.DIM_5TH,
        Grade.JUST_5TH,
        Grade.MINOR_6TH,
        Grade.MAYOR_6TH,
        Grade.MINOR_7TH,
        Grade.MAYOR_7TH
    ))
}

enum class Grade(
    val semitones: Int,
    val symbol: String,
) {
    TONIC(0, "T"),
    MINOR_2ND(1, "2m"),
    MAYOR_2ND(2, "2M"),
    MINOR_3ND(3, "3m"),
    MAYOR_3ND(4, "3M"),
    JUST_4TH(5, "4"),
    DIM_5TH(6, "5 dim"),
    JUST_5TH(7, "5"),
    MINOR_6TH(8, "6m"),
    MAYOR_6TH(9, "6M"),
    MINOR_7TH(10, "7m"),
    MAYOR_7TH(11, "7M"),
}

enum class Representation(val notes: Array<String>) {
    SHARP(
        arrayOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#")
    ),
    MAYOR_SHARP(
        arrayOf("B#", "C#", "D", "D#", "E", "E#", "F#", "G", "G#", "A", "A#", "B", "B#", "C#", "D", "D#", "E", "E#", "F#", "G", "G#", "A", "A#")
    ),
    MAYOR_DOUBLE_SHARP(
        arrayOf("B#", "C#", "C##", "D#", "E", "E#", "F#", "F##", "G#", "A", "A#", "B", "B#", "C#", "C##", "D#", "E", "E#", "F#", "G", "G#", "A", "A#")
    ),
    FLAT(
        arrayOf("C", "D\u266D", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A", "B♭", "B", "C", "D♭", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A", "B♭")
    ),
    
}

fun getRepresentation(scale: Scale, tonic: Tonic) = when {
    scale == Scale.MAYOR && tonic in listOf(Tonic.C_SH) -> Representation.MAYOR_SHARP
    scale == Scale.MAYOR && tonic in listOf(Tonic.D_SH) -> Representation.MAYOR_DOUBLE_SHARP
    scale == Scale.MAYOR && tonic in listOf(Tonic.D_B) -> Representation.FLAT
    scale == Scale.MINOR && tonic in listOf(Tonic.C) -> Representation.FLAT
    else -> Representation.SHARP
}

