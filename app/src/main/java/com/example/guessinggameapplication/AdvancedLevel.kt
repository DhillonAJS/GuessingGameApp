package com.example.guessinggameapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class AdvancedLevel : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Rreference: https://www.youtube.com/watch?v=f-M_S12FyBw
            val scrollState = rememberScrollState()
            AdvancedLevelGUI(scrollState = scrollState)
        }
    }
}

@Composable
fun AdvancedLevelGUI(scrollState: ScrollState) {
    var displayedFlags by rememberSaveable { mutableStateOf(emptyList<Int>()) }
    var userInput1 by rememberSaveable { mutableStateOf("") }
    var userInput2 by rememberSaveable { mutableStateOf("") }
    var userInput3 by rememberSaveable { mutableStateOf("") }
    var isUserInput1Correct by rememberSaveable { mutableStateOf(false) }
    var isUserInput2Correct by rememberSaveable { mutableStateOf(false) }
    var isUserInput3Correct by rememberSaveable { mutableStateOf(false) }
    var attempts by rememberSaveable { mutableIntStateOf(0) }
    var score by rememberSaveable { mutableIntStateOf(0) }

    fun getRandomFlags() {
        displayedFlags = List(3) { drawableIds[Random.nextInt(drawableIds.size)] }
    }

    fun checkAnswers() {
        isUserInput1Correct = userInput1.equals(flagNames[drawableIds.indexOf(displayedFlags[0])], ignoreCase = true)
        isUserInput2Correct = userInput2.equals(flagNames[drawableIds.indexOf(displayedFlags[1])], ignoreCase = true)
        isUserInput3Correct = userInput3.equals(flagNames[drawableIds.indexOf(displayedFlags[2])], ignoreCase = true)
        if (!isUserInput1Correct || !isUserInput2Correct || !isUserInput3Correct) {
            attempts++
        }

        score = (if (isUserInput1Correct) 1 else 0) +
                (if (isUserInput2Correct) 1 else 0) +
                (if (isUserInput3Correct) 1 else 0)
    }

    fun reset() {
        userInput1 = ""
        userInput2 = ""
        userInput3 = ""
        isUserInput1Correct = false
        isUserInput2Correct = false
        isUserInput3Correct = false
        attempts = 0
        getRandomFlags()
    }

        if (displayedFlags.isEmpty()) {
            getRandomFlags()
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // display score on the top right
        Text(
            text = "Score: $score",
            modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp)
        )

        // show image and text field for each flag

        // Reference: https://www.tutorialspoint.com/how-to-get-the-current-index-of-an-array-while-using-foreach-loop-in-kotlin
        //            https://stackoverflow.com/questions/48898102/how-to-get-the-current-index-in-for-each-kotlin
        displayedFlags.forEachIndexed { index, flag ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = flag),
                    contentDescription = "Flag",
                    modifier = Modifier.size(150.dp)
                )

                val userInput = when (index) {
                    0 -> userInput1
                    1 -> userInput2
                    2 -> userInput3
                    else -> ""
                }

                TextField(
                    value = userInput,
                    onValueChange = { value ->
                        when (index) {
                            0 -> userInput1 = value
                            1 -> userInput2 = value
                            2 -> userInput3 = value
                        }
                    },
                    label = { Text("Enter country name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    enabled = when (index) {
                        0 -> !isUserInput1Correct
                        1 -> !isUserInput2Correct
                        2 -> !isUserInput3Correct
                        else -> true
                    },
                    placeholder = {
                        Text(
                            text = userInput,
                            color = Color.Black,
                        )
                    })
            }
        }

        if (attempts >= 3 && (!isUserInput1Correct || !isUserInput2Correct || !isUserInput3Correct)) {
            Text(
                text = "Wrong!",
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp))
            if (!isUserInput1Correct) Text(
                text = "Flag 1: ${flagNames[drawableIds.indexOf(displayedFlags[0])]}",
                color = Color.Blue,
                modifier = Modifier.padding(top = 8.dp))
            if (!isUserInput2Correct) Text(
                text = "Flag 2: ${flagNames[drawableIds.indexOf(displayedFlags[1])]}",
                color = Color.Blue,
                modifier = Modifier.padding(top = 8.dp))
            if (!isUserInput3Correct) Text(
                text = "Flag 3: ${flagNames[drawableIds.indexOf(displayedFlags[2])]}",
                color = Color.Blue,
                modifier = Modifier.padding(top = 8.dp))
        } else if (isUserInput1Correct && isUserInput2Correct && isUserInput3Correct) {
            Text(
                text = "Correct!",
                color = Color.Green,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Button(
            onClick = {
                if (attempts >= 3 && (!isUserInput1Correct || !isUserInput2Correct || !isUserInput3Correct)) {
                    reset()
                } else if (isUserInput1Correct && isUserInput2Correct && isUserInput3Correct) {
                    reset()
                } else {
                    checkAnswers()
                }
            },
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        ) {
            Text(if (attempts >= 3 && (!isUserInput1Correct || !isUserInput2Correct || !isUserInput3Correct)) "Next" else "Submit")
        }
    }
}