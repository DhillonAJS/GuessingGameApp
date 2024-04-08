package com.example.guessinggameapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// images from https://github.com/hampusborgos/country-flags
val drawableIds = listOf(
    R.drawable.ae, R.drawable.au, R.drawable.bs, R.drawable.ca,
    R.drawable.cn, R.drawable.cu, R.drawable.cy, R.drawable.es,
    R.drawable.fi, R.drawable.fr, R.drawable.gb, R.drawable.hk,
    R.drawable.ie, R.drawable.`in`, R.drawable.it, R.drawable.lb,
    R.drawable.nz, R.drawable.pl, R.drawable.ps, R.drawable.pt,
    R.drawable.qa, R.drawable.ru, R.drawable.se, R.drawable.ua,
    R.drawable.usa
)

val flagNames = listOf(
    "United Arab Emirates", "Australia", "Bahamas", "Canada", "China", "Cuba",
    "Cyprus", "Spain", "Finland", "France", "Great Britain", "Hong Kong",
    "Ireland", "India", "Italy", "Lebanon", "New Zealand", "Poland", "Palestine",
    "Portugal", "Qatar", "Russia", "Sweden", "Ukraine", "United States of America"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisplayGUI()
        }
    }
}

@Composable
fun DisplayGUI() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        val context = LocalContext.current

        Text(
            text = "Welcome to the Guessing Game",
            fontSize = 27.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Text(
            text = "Please choose a game to play:",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        //Reference (changing color of buttons): https://stackoverflow.com/questions/64376333/background-color-on-button-in-jetpack-compose
        Button(
            onClick = {
                // Start a new activity for guessing the country with a random flag
                val i = Intent(context, GuessTheCountry::class.java)
                context.startActivity(i)
            },
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        )
        {
            Text(text = "Guess the Country")
        }

        Button(
            onClick = {
                // Start a new activity for guessing the country with a hints
                val i = Intent(context, GuessHints::class.java)
                context.startActivity(i)
            },
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        )
        {
            Text(text = "Guess-Hints")
        }

        Button(
            onClick = {
                // Start a new activity for guessing the flag from the country name
                val i = Intent(context, GuessTheFlag::class.java)
                context.startActivity(i)
            },
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        )
        {
            Text(text = "Guess the Flag")
        }

        Button(
            onClick = {
                // Start a new activity for guessing the country with a random flag by typing
                val i = Intent(context, AdvancedLevel::class.java)
                context.startActivity(i)
            },
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        )
        {
            Text(text = "Advanced Level")
        }
    }
}