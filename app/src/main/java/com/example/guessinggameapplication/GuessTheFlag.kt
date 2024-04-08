package com.example.guessinggameapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kotlin.random.Random
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class GuessTheFlag : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessTheFlagGUI()
        }
    }
}

@Preview
@Composable
fun GuessTheFlagGUI() {
    var flagOne by rememberSaveable { mutableIntStateOf(0) }
    var flagTwo by rememberSaveable { mutableIntStateOf(0) }
    var flagThree by rememberSaveable { mutableIntStateOf(0) }

    var clicked by rememberSaveable { mutableStateOf(true) }
    var answer by rememberSaveable { mutableStateOf("") }
    var flagChoice by rememberSaveable { mutableStateOf(0) }

    // initialise the flags
    if (clicked) {

        val newDrawables = chooseNewFlags()
        flagOne = newDrawables[0]
        flagTwo = newDrawables[1]
        flagThree = newDrawables[2]
        clicked = false

        // choose one of the 3 images as the flag to identify
        flagChoice = Random.nextInt(3)

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Row (
                modifier = Modifier.padding(16.dp),
                // https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/Arrangement#spacedBy(androidx.compose.ui.unit.Dp)
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ){
                // display three flag images
                Image(painterResource(id = flagOne),
                    contentDescription = "Flag 1",
                    modifier = Modifier
                        .border(BorderStroke(1.dp, Color.Black))
                        .clickable {
                            if (flagChoice == 0) {
                                answer = "CORRECT!"
                            } else {
                                answer = "WRONG!"
                            }
                        }
                )

                Image(painterResource(id = flagTwo),
                    contentDescription = "Flag 2",
                    modifier = Modifier
                        // https://stackoverflow.com/questions/75410989/jetpack-compose-border-for-image-reduces-padding
                        .border(BorderStroke(1.dp, Color.Black))
                        .clickable {
                            if (flagChoice == 1) {
                                answer = "CORRECT!"
                            } else {
                                answer = "WRONG!"
                            }
                        }
                )

                Image(painterResource(id = flagThree),
                    contentDescription = "Flag 3",
                    modifier = Modifier
                        .border(BorderStroke(1.dp, Color.Black))
                        .clickable {
                            if (flagChoice == 2) {
                                answer = "CORRECT!"
                            } else {
                                answer = "WRONG!"
                            }
                        }
                )
            }

        // display the name of the flag
        val flag: String = flagNames[if (flagChoice == 0) drawableIds.indexOf(flagOne)
                                      else if (flagChoice == 1) drawableIds.indexOf(flagTwo)
                                      else drawableIds.indexOf(flagThree)]

        Text(
            text = flag,
            modifier = Modifier.padding(vertical = 15.dp),
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

            // display correct or incorrect when clicked
            Text(
                text = answer,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                // changing the text color: https://developer.android.com/jetpack/compose/text/style-text
                color = if (answer == "CORRECT!") Color.Green else Color.Red
                )

        // button to move to the next set of flags
        Button(
            onClick = {
                answer = ""
                clicked = true
                val newDrawables = chooseNewFlags()
                flagOne = newDrawables[0]
                flagTwo = newDrawables[1]
                flagThree = newDrawables[2]
                flagChoice = Random.nextInt(3)
            },
            // adding padding to modifiers: https://developer.android.com/jetpack/compose/modifiers
            modifier = Modifier.padding(16.dp),

            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        ) {
            Text("Next")
        }
        }
    }

// return the ids of drawable for 3 new flags
fun chooseNewFlags(): List<Int> {
    val flagList = mutableListOf<Int>()

    val index1 = Random.nextInt(flagNames.size)
    val flag1 = flagNames[index1]

    var index2 = Random.nextInt(flagNames.size)
    var flag2 = flagNames[index2]
    while (flag1 == flag2) {
        index2 = Random.nextInt(flagNames.size)
        flag2 = flagNames[index2]
    }

    var index3 = Random.nextInt(flagNames.size)
    var flag3 = flagNames[index3]
    while (flag1 == flag3 || flag2 == flag3) {
        index3 = Random.nextInt(flagNames.size)
        flag3 = flagNames[index3]
    }

    // add the 3 drawables in the list
    flagList.add(drawableIds[index1])
    flagList.add(drawableIds[index2])
    flagList.add(drawableIds[index3])

    return flagList
}
