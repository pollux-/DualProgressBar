package com.pollux.dualprogressview.ui

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DualProgressView(
    modifier: Modifier = Modifier,
    radius: Int = 100,
    innerCircleColor: Color = MaterialTheme.colors.primary,
    outerCircleColor: Color = MaterialTheme.colors.secondary,
    strokeWidth: Dp = 4.dp,
    ) {


    val infiniteTransition = rememberInfiniteTransition()

    //Fractional start angle
    val startAngle by infiniteTransition.fraction(1000)
    //Fractional sweep angle
    val sweepAngle by infiniteTransition.fraction(1000)


    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
    }


    Box(modifier = modifier
        .size(radius.dp)
        .padding(16.dp),
        contentAlignment = Alignment.Center

    ) {
        Canvas(
            modifier = modifier
                .size(radius.dp / 2) // use 50% radius size as width & height
                .padding(16.dp)


        ) {

            // inner Circle
            drawArc(color = innerCircleColor,
                startAngle = startAngle * 360F,
                sweepAngle = 360 * sweepAngle,
                useCenter = false,
                style = stroke)
        }

        // outer circle
        Canvas(
            modifier = Modifier
                .size(radius.dp * 0.8F) // use 80% radius size as width & height
                .padding(16.dp)

        ) {
            rotate(180F) {
                drawArc(color = outerCircleColor,
                    startAngle = startAngle * 360F,
                    sweepAngle = 360 * sweepAngle,
                    useCenter = false,
                    style = stroke)
            }
        }


    }

}

@Composable
internal fun InfiniteTransition.fraction(
    durationMillis: Int,
    delayMillis: Int = 0,
    easing: Easing = FastOutSlowInEasing,
): State<Float> {
    val duration = durationMillis + delayMillis

    return animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                this.durationMillis = duration
                0f at delayMillis / 2 with easing
                1f at durationMillis + (delayMillis / 2)
                1f at duration
            }
        )
    )
}