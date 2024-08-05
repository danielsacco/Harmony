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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.des.harmony.model.view.ScaleViewModel
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
        ScalesScreen()
    }
}

@Composable
fun ScalesScreen(
    modifier: Modifier = Modifier,
    scaleViewModel: ScaleViewModel = viewModel()
) {

    val scaleShowState by scaleViewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            //.fillMaxHeight()
            //.height(500.dp)
    ) {
        // Scale Selector
        SelectorStrip(
            texts = scaleViewModel.scalesToShowOnSelector,
            selectedIndex = scaleViewModel.selectedScaleIndex,
            onSelect = scaleViewModel::selectScale,
            modifier = modifier.width(80.dp)
        )
        // Pitch Selector
        SelectorStrip(
            texts = scaleShowState.notesToShowOnSelector,
            selectedIndex = scaleViewModel.selectedPitchIndex,
            onSelect = scaleViewModel::selectPitch,
            modifier = modifier.width(30.dp)
        )

        val notesToShowOnWheel = scaleViewModel.notesToShow

        NotesWheel(
            notesList = notesToShowOnWheel,
        )
    }
}


@Composable
private fun NotesWheel(
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

val textDp = 25.dp
val spaceDp = 6.dp