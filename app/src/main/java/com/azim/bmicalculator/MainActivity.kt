package com.azim.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azim.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMICalculatorScreen()
        }
    }
}

@Composable
fun BMICalculatorScreen() {
    BMICalculatorTheme {
        // Scaffold is used for setting up common elements like top bar
        Scaffold(
            modifier = Modifier.padding(8.dp),
            topBar = {
                // Add your top bar here if needed
                Text(text = "BMI Calculator", fontSize = 24.sp, color = Color.Black, modifier = Modifier.padding(16.dp))
            }
        ) {
            BMICalculator() // This is where your actual BMI calculator UI will be
        }
    }
}

@Composable
fun BMICalculator() {
    // Initial values for weight and height
    var weight by remember { mutableStateOf(70f) }
    var height by remember { mutableStateOf(170f) }

    // Calculate BMI
    val bmi = if (height > 0) {
        weight / ((height / 100) * (height / 100)) // Formula: BMI = weight (kg) / height (m)²
    } else {
        0f // Return 0 if height is 0 to avoid division by zero
    }

    // Determine BMI category
    val bmiCategory = when {
        bmi < 18.5 -> "Underweight"
        bmi in 18.5..24.9 -> "Normal weight"
        bmi in 25.0..29.9 -> "Overweight"
        else -> "Obese"
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title Text
        Text(text = "BMI Calculator", fontSize = 32.sp, color = Color.Blue)

        Spacer(modifier = Modifier.height(16.dp))

        // Subtitle Text
        Text(text = "We care about your health", fontSize = 24.sp, color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        // Image for BMI (make sure the drawable is added to resources)
        Image(painter = painterResource(id = R.drawable.bmi), contentDescription = "BMI Image")

        Spacer(modifier = Modifier.height(24.dp))

        // Height Slider
        Text(text = "Height : ${height.toInt()} cm")
        Slider(
            value = height,
            onValueChange = { height = it },
            valueRange = 100f..250f,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Weight Slider
        Text(text = "Weight : ${weight.toInt()} kg")
        Slider(
            value = weight,
            onValueChange = { weight = it },
            valueRange = 50f..200f,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Card displaying the BMI result
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD)
            ),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display BMI result
                Text(text = "Your BMI is: %.2f".format(bmi), fontSize = 24.sp)

                Spacer(modifier = Modifier.height(16.dp))

                // Display BMI category
                Text(text = "Category: $bmiCategory", fontSize = 18.sp, color = Color.Gray)
            }
        }
    }
}
