package com.example.guessinggameapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class GuessTheCountry : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessTheCountryGUI()
        }
    }
}

@Composable
fun GuessTheCountryGUI() {
    // Lecture 5 - Adding rememberSaveable to remember configuration changes
    var currentFlag by rememberSaveable { mutableIntStateOf(getRandomFlag()) }
    var selectedCountry by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf(false) }
    var correct by rememberSaveable { mutableStateOf(false) }
    var buttonText by rememberSaveable { mutableStateOf("Submit") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // display the flag
        Image(
            painterResource(id = drawableIds[currentFlag]),
            contentDescription = "Flag",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(bottom = 10.dp, top = 5.dp)
        )

        // display the list of countries
        // Lecture 5 - List with Selectable Items
        // https://stackoverflow.com/questions/66793855/compose-lazycolumn-select-one-item
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(flagNames) { country ->
                country(
                    country = country,
                    selected = selectedCountry == country,
                    onClick = {
                        selectedCountry = it // Lecture 7 - Lambda Expression
                    }
                )
            }
        }

        // display the submit/next button
        Button(
            onClick = {
                if (buttonText == "Submit") {
                    // when the submit button is clicked
                    result = true
                    correct = selectedCountry == (flagNames[currentFlag])
                    buttonText = "Next"
                } else {
                    // when the next button is clicked
                    currentFlag = getRandomFlag()
                    selectedCountry = ""
                    result = false
                    correct = false
                    buttonText = "Submit"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),

            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        ) {
            Text(buttonText)
        }

        // display the result message
        if (result) {
            val resultMessage = if (correct) "CORRECT!" else "WRONG!"
            val color = if (correct) Color.Green else Color.Red
            val correctCountry = flagNames[currentFlag]

            Text(
                text = resultMessage,
                color = color,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Text(
                text = "Correct Country: $correctCountry",
                color = Color.Blue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun country(country: String, selected: Boolean, onClick: (String) -> Unit) {
    // background color for selected item to show it's been selected
    val backgroundColor = if (selected) Color.LightGray else Color.Transparent
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(country) }
            .background(color = backgroundColor)
            .padding(8.dp)
    ) {
        Text(text = country)
    }
}

fun getRandomFlag(): Int {
    return Random.nextInt(drawableIds.size)
}