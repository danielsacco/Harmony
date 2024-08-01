package com.des.harmony

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.des.harmony.ui.theme.HarmonyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HarmonyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
// Creating a Column Layout
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WheelAndSelector()

        
    }
}

@Composable
fun WheelAndSelector(
    modifier: Modifier = Modifier,
) {

    var scale: Scale by remember { mutableStateOf(Scale.MAYOR) }

    var tonic by remember { mutableStateOf(Tonic.C) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            //.fillMaxSize()
            .fillMaxWidth()
            .height(500.dp)
    ) {
        Selector(
            selectedScale = scale,
            onSelectScale = { newScale -> scale = newScale },
            selectedTonic = tonic,
            onSelectTonic = { newTonic -> tonic = newTonic },
        )

        Spacer(modifier = Modifier.size(20.dp))

        val notesArray: Array<String?> = getNotesArrayFor(tonic, scale)

        NewWheel(notesArray = notesArray)
    }
}

fun getNotesArrayFor(tonic: Tonic, scale: Scale): Array<String?> =
    when (scale) {
        Scale.MAYOR -> {
            when(tonic) {
                Tonic.C -> MAJOR_C
                Tonic.C_SH -> MAJOR_C_SH
                Tonic.D_FL -> MAJOR_D_FL
                Tonic.D -> MAJOR_D
                Tonic.D_SH -> MAJOR_D_SH
                Tonic.E_FL -> MAJOR_E_FL
                Tonic.E -> MAJOR_E
                Tonic.F -> MAJOR_F
                Tonic.F_SH -> MAJOR_F_SH
                Tonic.G_FL -> MAJOR_G_FL
                Tonic.G -> MAJOR_G
                Tonic.G_SH -> MAJOR_G_SH
                Tonic.A_FL -> MAJOR_A_FL
                Tonic.A -> MAJOR_A
                Tonic.A_SH -> MAJOR_A_SH
                Tonic.B_FL -> MAJOR_B_FL
                Tonic.B -> MAJOR_B
            }

        }
        Scale.MINOR -> {
            when(tonic) {
                Tonic.C -> MINOR_C
                Tonic.C_SH -> TODO()
                Tonic.D_FL -> TODO()
                Tonic.D -> TODO()
                Tonic.D_SH -> TODO()
                Tonic.E_FL -> TODO()
                Tonic.E -> TODO()
                Tonic.F -> TODO()
                Tonic.F_SH -> TODO()
                Tonic.G_FL -> TODO()
                Tonic.G -> TODO()
                Tonic.G_SH -> TODO()
                Tonic.A_FL -> TODO()
                Tonic.A -> TODO()
                Tonic.A_SH -> TODO()
                Tonic.B_FL -> TODO()
                Tonic.B -> TODO()
            }
        }

    }



@Composable
private fun NewWheel(
    notesArray: Array<String?>,
) {
    val textMeasurer = rememberTextMeasurer()

    val notes = if(notesArray.size == 12) notesArray else emptySemitonesArray

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val rimWidth = (textDp + spaceDp).toPx()

        val lineVerticalEnd = center.y - (size.minDimension / 2 - rimWidth * 2.6f)
        val lineEnd = center.copy(y = lineVerticalEnd)

        notes.forEachIndexed { index, note ->
            note?.let {
                // Draw lines
                withTransform({
                    rotate(DELTA_ANGLE * index)
                }) {
                    line(center, lineEnd)
                }

                val notesRadius = (size.minDimension / 2) - rimWidth / 2
                drawTextOnCircle(textMeasurer, note, index, notesRadius)

                val intervalsRadius = (size.minDimension / 2) - rimWidth * 1.9f
                drawTextOnCircle(textMeasurer, intervals[index], index, intervalsRadius)
                //drawInterval(textMeasurer, index, intervalsRadius)
            }
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
            radius = (size.minDimension / 2) - rimWidth * 1.2f,
            style = Stroke(5F)
        )

        drawCircle(
            color = Color(0xff0f9d58),
            center = center,
            radius = (size.minDimension / 2) - rimWidth * 2.6f,
            style = Stroke(5F)
        )
    }
}

private fun DrawScope.drawTextOnCircle(
    textMeasurer: TextMeasurer,
    text: String,
    index: Int,
    radius: Float
) {
    val noteSize = textMeasurer.measure(text).size

    val xPos = size.center.x + radius * xFactor[index] * 2
    val yPos = size.center.y + radius * yFactor[index] * 2

    val position = Offset(
        x = xPos - noteSize.center.x,
        y = yPos - noteSize.center.y,
    )

    val fontWeight = if(index ==0) FontWeight.Bold else FontWeight.Normal
    
    drawText(
        textMeasurer = textMeasurer,
        text = text,
        topLeft = position,
        style = TextStyle.Default.copy(
            fontWeight = fontWeight,
            fontSize = 20.sp,
        )
    )
}

@Composable
fun Selector(
    modifier: Modifier = Modifier,
    onSelectScale: (scale: Scale) -> Unit = {},
    selectedScale: Scale = Scale.MAYOR,
    onSelectTonic: (tonic: Tonic) -> Unit = {},
    selectedTonic: Tonic = Tonic.C,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Scale.entries.forEach { scale ->
                if( scale == selectedScale) {
                    Button(onClick = { }) {
                        Text(text = scale.scaleName)
                    }
                } else {
                    ElevatedButton(onClick = {
                        onSelectScale(scale)
                        //onSelectTonic(selectedTonic)
                    }) {
                        Text(text = scale.scaleName)
                    }
                }
            }
        }

        val representation: Representation =
            when(selectedScale) {
                Scale.MAYOR -> Representation.FLAT_SELECTOR
                Scale.MINOR -> Representation.SHARP_SELECTOR
                else -> Representation.CHROMATIC_SELECTOR
            }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            representation.notes.slice(0..<SEMITONES).forEachIndexed {
                index, note ->
                if(index == selectedTonic.offsetFromC) {
                    Button(
                        onClick = { onSelectTonic(Tonic.fromStringOrDefault(note)) },
                        modifier = Modifier.width(30.dp),
                        contentPadding = PaddingValues(horizontal = 0.dp)
                    ) {
                        Text(
                            text = note,
                            fontWeight = FontWeight.W100
                        )
                    }
                } else {
                    TextButton(
                        onClick = { onSelectTonic(Tonic.fromStringOrDefault(note)) },
                        modifier = Modifier.width(30.dp),
                        contentPadding = PaddingValues(horizontal = 0.dp)
                    ) {
                        Text(
                            text = note,
                            fontSize = 12.sp,
                        )
                    }
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

val MAJOR_C: Array<String?> = arrayOf("C", null, "D", null, "E", "F", null, "G", null, "A", null, "B")
val MAJOR_C_SH: Array<String?> = arrayOf("C#", null, "D#", null, "E#", "F#", null, "G#", null, "A#", null, "B#")
val MAJOR_D_FL: Array<String?> = arrayOf("D♭", null, "E♭", null, "F", "G♭", null, "A♭", null, "B♭", null, "C")
val MAJOR_D: Array<String?> = arrayOf("D", null, "E", null, "F#", "G", null, "A", null, "B", null, "C#")
val MAJOR_D_SH: Array<String?> = arrayOf("D#", null, "E#", null, "F##", "G#", null, "A#", null, "B#", null, "C##")
val MAJOR_E_FL: Array<String?> = arrayOf("E♭", null, "F", null, "G", "A♭", null, "B♭", null, "C", null, "C")
val MAJOR_E: Array<String?> = arrayOf("E", null, "F#", null, "G#", "A", null, "B", null, "C", null, "D#")
val MAJOR_F: Array<String?> = arrayOf("F", null, "G", null, "A", "B♭", null, "C", null, "D", null, "E")
val MAJOR_F_SH: Array<String?> = arrayOf("F#", null, "G#", null, "A#", "B", null, "C#", null, "D#", null, "E#")
val MAJOR_G_FL: Array<String?> = arrayOf("G♭", null, "A♭", null, "B♭", "C♭", null, "D♭", null, "E♭", null, "F")
val MAJOR_G: Array<String?> = arrayOf("G", null, "A", null, "B", "C", null, "D", null, "E", null, "F#")
val MAJOR_G_SH: Array<String?> = arrayOf("G#", null, "A#", null, "B#", "C#", null, "D#", null, "E#", null, "F##")
val MAJOR_A_FL: Array<String?> = arrayOf("A♭", null, "B♭", null, "C", "D♭", null, "E♭", null, "F", null, "G")
val MAJOR_A: Array<String?> = arrayOf("A", null, "B", null, "C#", "D", null, "E", null, "F#", null, "G#")
val MAJOR_A_SH: Array<String?> = arrayOf("A#", null, "B#", null, "C##", "D#", null, "E#", null, "F##", null, "G##")
val MAJOR_B_FL: Array<String?> = arrayOf("B♭", null, "C", null, "D", "E♭", null, "F", null, "G", null, "A")
val MAJOR_B: Array<String?> = arrayOf("B", null, "C#", null, "D#", "E", null, "F#", null, "G#", null, "A#")

// TODO !!!
val MINOR_C: Array<String?> = arrayOf("C", null, "D", "E♭", null, "F", null, "G", "Ab", null, "B♭", null)

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    HarmonyTheme {
        MainScreen()
    }
}

//@Preview(showBackground = true)
@Composable
fun NewWheelPreview() {

    HarmonyTheme {
        NewWheel(
            notesArray = MINOR_C,
        )
    }
}

val emptySemitonesArray: Array<String?> = Array(12) { null }

val intervals: Array<String> = arrayOf("T", "2m", "2M", "3m", "3M", "4", "4A", "5", "6m", "6M", "7m", "7M")

const val SEMITONES = 12
const val DELTA_ANGLE = 360F / SEMITONES

// X and Y offset for each semitone positioning
val xFactor = arrayOf(0f, 0.25f, 0.433f, 0.5f, 0.433f, 0.25f, 0f, - 0.25f, -0.433f, -0.5f, -0.433f, -0.25f)
val yFactor = arrayOf(-0.5f, -0.433f, -0.25f, 0f, 0.25f, 0.433f, 0.5f, 0.433f, 0.25f, 0f, -0.25f, -0.433f)

enum class Tonic(val offsetFromC: Int, val representation: String) {
    C(0, "C"),
    C_SH(1, "C#"),
    D_FL(1, "D♭"),
    D(2, "D"),
    D_SH(3, "D#"),
    E_FL(3, "E♭"),
    E(4, "E"),
    F(5, "F"),
    F_SH(6, "F#"),
    G_FL(6, "G♭"),
    G(7, "G"),
    G_SH(8, "G#"),
    A_FL(8, "A♭"),
    A(9, "A"),
    A_SH(10, "A#"),
    B_FL(10, "B♭"),
    B(11, "B");

    companion object {
        fun fromStringOrDefault(note: String): Tonic {
            return Tonic.entries.find {
                it.representation == note
            } ?: C
        }
    }
}

enum class Scale(val scaleName: String) {
    MAYOR("Major"),
    MINOR("Minor"),
    //CHROMATIC("Chromatic")
}

enum class Representation(val notes: Array<String>) {
    SHARP(
        arrayOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#")
    ),
    SHARP_E(
        arrayOf("C", "C#", "D", "D#", "E", "E#", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "E#", "F#", "G", "G#", "A", "A#")
    ),
    SHARP_B(
        arrayOf("B#", "C#", "D", "D#", "E", "E#", "F#", "G", "G#", "A", "A#", "B", "B#", "C#", "D", "D#", "E", "E#", "F#", "G", "G#", "A", "A#")
    ),
    FLAT(
        arrayOf("C", "D♭", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A", "B♭", "B", "C", "D♭", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A", "B♭")
    ),
    G_FLAT_MAJOR(
        arrayOf("C", "D♭", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A", "B♭", "C♭", "C", "D♭", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A", "B♭")
    ),
    FLAT_SELECTOR(
        arrayOf("C", "D♭", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A", "B♭", "B")
    ),
    SHARP_SELECTOR(
        arrayOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
    ),
    CHROMATIC_SELECTOR(
        arrayOf("C", "C#/D♭", "D", "D#/E♭", "E", "F", "F#/G♭", "G", "G#/A♭", "A", "A#/B♭", "B")
    )

}

val textDp = 25.dp
val spaceDp = 6.dp