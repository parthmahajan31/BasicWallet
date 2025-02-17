package com.task.vpdmoney.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.vpdmoney.ui.theme.Purple80
import com.task.vpdmoney.ui.theme.colorBackgroundWhite
import com.task.vpdmoney.ui.theme.colorTransparent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppFilledButton(
    modifier: Modifier = Modifier,
    text: String = "",
    backgroundColor: Color = Purple80,
    radius: Dp = 10.dp,
    textColor: Color = colorBackgroundWhite,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight = FontWeight.Medium,
    fontSize: TextUnit = 14.sp,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
) {
    var isClickable by remember { mutableStateOf(true) }

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(radius),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = backgroundColor.copy(alpha = 0.5f)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        enabled = enabled,
        contentPadding = contentPadding,
        onClick = {
            if (isClickable) {
                // Disable clicking to prevent multiple clicks
                isClickable = false
                onClick()
                // Coroutine to re-enable clicking after a delay
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    isClickable = true
                }
            }
        },
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (isLoading) 0f else 1f),
                text = text,
                style = textStyle,
                fontWeight = fontWeight,
                fontSize = fontSize,
                color = textColor,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (isLoading) {
                AppButtonLoader(
                    trackColor = backgroundColor,
                    color = textColor,
                )
            }
        }
    }
}

@Composable
fun AppButtonLoader(
    modifier: Modifier = Modifier,
    trackColor: Color = colorTransparent,
    color: Color = colorBackgroundWhite,
) {
    CircularProgressIndicator(
        modifier = modifier.size(20.dp),
        strokeCap = StrokeCap.Butt,
        trackColor = trackColor,
        strokeWidth = 2.dp,
        color = color,
    )
}