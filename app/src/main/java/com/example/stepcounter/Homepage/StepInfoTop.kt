package com.example.stepcounter.Homepage

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stepcounter.ui.theme.md_theme_light_background
import com.example.stepcounter.ui.theme.md_theme_light_onSecondary
import com.example.stepcounter.ui.theme.md_theme_light_tertiary

class StepInfoTop {
    @Composable
    fun StepsInfoSection(
        totalSteps : Float,
        size: Dp = 150.dp,
        target: Int = 500,
        shadowColor: Color = Color.LightGray,
        indicatorThickness: Dp = 8.dp,
        animationDuration: Int = 1000,
    ){
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        var stepsTakenRemember by remember { mutableFloatStateOf(0f) }
        var progress by remember { mutableStateOf(0.1f) }

        val animatedProgress = animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec, label = ""
        ).value

        // This is to animate the foreground indicator
        val stepsTakenAnimate = animateFloatAsState(
            targetValue = totalSteps,
            animationSpec = tween(
                durationMillis = animationDuration
            ), label = ""
        )

        // This is to start the animation when the activity is opened
        LaunchedEffect(Unit) {
            stepsTakenRemember = totalSteps
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 2)
                .padding(20.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RectangleShape,
                    spotColor = Color.Black
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Box(
                    modifier = Modifier
                        .size(size)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(size)
                    ) {
                        // For shadow
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(shadowColor, Color.White),
                                center = Offset(x = this.size.width / 2, y = this.size.height / 2),
                                radius = this.size.height / 2
                            ),
                            radius = this.size.height / 2,
                            center = Offset(x = this.size.width / 2, y = this.size.height / 2)
                        )

                        // This is the white circle that appears on the top of the shadow circle
                        drawCircle(
                            color = md_theme_light_background,
                            radius = (size / 2 - indicatorThickness).toPx(),
                            center = Offset(x = this.size.width / 2, y = this.size.height / 2)
                        )

                        // Convert the stepsTaken to angle
                        val sweepAngle = (stepsTakenAnimate.value) * 360 / target

                        // Foreground indicator
                        drawArc(
                            color = md_theme_light_tertiary,
                            startAngle = -90f,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = Stroke(
                                width = indicatorThickness.toPx(),
                                cap = StrokeCap.Round
                            ),
                            size = Size(
                                width = (size - indicatorThickness).toPx(),
                                height = (size - indicatorThickness).toPx()
                            ),
                            topLeft = Offset(
                                x = (indicatorThickness / 2).toPx(),
                                y = (indicatorThickness / 2).toPx()
                            )
                        )
                    }

                    // Display the data usage value
                    DisplayText(stepsTakenAnimate.value)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(md_theme_light_onSecondary)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(10.dp),
                        ) {
                            Text(text = "Remaining", fontSize = 14.sp)
                            Text(text = (target - totalSteps.toInt()).toString(), fontSize = 18.sp)
                        }
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Text(text = "Target", fontSize = 14.sp)
                            Text(text = target.toString(), fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun DisplayText(
        animateNumber: Float
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Text that shows the number inside the circle
            Text(
                text = (animateNumber).toInt().toString(),
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Steps",
                fontSize = 16.sp,
            )
        }
    }

}