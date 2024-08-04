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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.des.harmony.model.NotesSolver.notesFor
import com.des.harmony.model.enums.Accidental
import com.des.harmony.model.enums.PitchClass
import com.des.harmony.model.enums.Scale
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

//    var scale: Scale by remember { mutableStateOf(Scale.MAYOR) }
//
//    var tonic by remember { mutableStateOf(Tonic.C) }

    var selectedScale by remember { mutableStateOf(Scale.MAYOR) }

    var selectedPitchClass by remember { mutableStateOf(PitchClass.C) }

    var selectedAccidental by remember { mutableStateOf(Accidental.NATURAL) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            //.fillMaxSize()
            .fillMaxWidth()
            .height(500.dp)
    ) {
        SelectorStrip(
            texts = Scale.entries.map { it.scaleName },
            selectedIndex = Scale.entries.indexOf(selectedScale),
            onSelect = { index ->
                selectedScale = Scale.entries[index]
            },
        )
        SelectorStrip(
            // TODO: strip based on selected scale
            texts = listOf(
                "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "B♭", "B"
            ),
            selectedIndex = PitchClass.entries.indexOf(selectedPitchClass),
            onSelect = { index ->
                selectedPitchClass = PitchClass.entries[index]
                selectedAccidental = if(selectedPitchClass.isNatural) {
                    Accidental.NATURAL
                } else {
                    // TODO: accidental based on selected scale
                    Accidental.SHARP
                }
            },
            modifier = modifier.width(30.dp)
        )

        NewWheel(
            notesList = notesFor(
                selectedScale = selectedScale,
                selectedPitchClass = selectedPitchClass,
                selectedAccidental = selectedAccidental
            )
        )
    }
}

@Composable
private fun NewWheel(
    notesList: List<String?>,
) {
    val textMeasurer = rememberTextMeasurer()

    val notes = if(notesList.size == 12) notesList else emptySemitonesList

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
fun SelectorStrip(
    modifier: Modifier = Modifier,
    texts: List<String> = listOf("A", "B", "C"),
    selectedIndex: Int = 0,
    onSelect: (Int) -> Unit = {},
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            texts.forEachIndexed { index, text ->
                if(index == selectedIndex) {
                    Button(
                        onClick = {  },
                        modifier = modifier,
                        contentPadding = PaddingValues(horizontal = 0.dp)
                    ) {
                        Text(
                            text = text,
                            fontWeight = FontWeight.W100
                        )
                    }
                } else {
                    TextButton(
                        onClick = { onSelect(index) },
                        modifier = modifier,
                        contentPadding = PaddingValues(horizontal = 0.dp)
                    ) {
                        Text(
                            text = text,
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    }
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
                Scale.MAYOR -> Representation.MAJOR_SELECTOR
                Scale.MINOR -> Representation.MINOR_SELECTOR
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    HarmonyTheme {
        MainScreen()
    }
}



const val SEMITONES = 12
const val DELTA_ANGLE = 360F / SEMITONES
val emptySemitonesList: List<String?> = List(SEMITONES) { null }
val intervals: List<String> = listOf("T", "2m", "2M", "3m", "3M", "4", "4A", "5", "6m", "6M", "7m", "7M")

// X and Y offset for each semitone positioning
val xFactor = listOf(0f, 0.25f, 0.433f, 0.5f, 0.433f, 0.25f, 0f, - 0.25f, -0.433f, -0.5f, -0.433f, -0.25f)
val yFactor = listOf(-0.5f, -0.433f, -0.25f, 0f, 0.25f, 0.433f, 0.5f, 0.433f, 0.25f, 0f, -0.25f, -0.433f)

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

enum class Representation(val notes: List<String>) {
    MAJOR_SELECTOR(
        listOf("C", "D♭", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A", "B♭", "B")
    ),
    MINOR_SELECTOR(
        listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "B♭", "B")
    ),
    CHROMATIC_SELECTOR(
        listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
    )

}

val textDp = 25.dp
val spaceDp = 6.dp