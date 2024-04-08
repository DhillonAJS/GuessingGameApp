package com.example.guessinggameapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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

class GuessHints : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessHintsGUI()
        }
    }
}

@Composable
fun GuessHintsGUI() {
    var flagIndex by rememberSaveable { mutableStateOf(-1) }
    var userInput by rememberSaveable { mutableStateOf("") }
    var displayText by rememberSaveable { mutableStateOf("") }
    var buttonText by rememberSaveable { mutableStateOf("Submit") }

    fun resetGameState() {
        flagIndex = Random.nextInt(drawableIds.size)
        displayText = flagNames[flagIndex].replace(Regex("[A-Za-z]"), "-")
        userInput = ""
        buttonText = "Submit"
    }

    if (flagIndex == -1) {
        resetGameState()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // display the flag
        Image(
            painter = painterResource(id = drawableIds[flagIndex]),
            contentDescription = "Flag"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = displayText)
        Spacer(modifier = Modifier.height(16.dp))

        // text field for the user input
        TextField(
            value = userInput,
            onValueChange = {
                if (it.length <= 1) { // stops the user from entering more than one letter
                    userInput = it
                }
            },
            label = { Text("Enter a letter") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Submit user input
        Button(onClick = {
            if (userInput.length > 0) {
                val guessedCharacter = userInput[0]
                val countryName = flagNames[flagIndex]
                var newText = ""
                var allLettersGuessed = true

                // checks if the guessed letter is in the flag name
                for (char in countryName) {
                    if (char.equals(guessedCharacter, ignoreCase = true)) {
                        newText += char
                    } else {
                        if (displayText.contains(char, ignoreCase = true)) {
                            newText += displayText[countryName.indexOf(char)]
                        } else {
                            newText += " - "
                            allLettersGuessed = false
                        }
                    }
                }

                displayText = newText

                if (allLettersGuessed) {
                    resetGameState()
                }
            }
            userInput = ""
        },
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        ) {
            Text(text = buttonText)
        }
    }
}
