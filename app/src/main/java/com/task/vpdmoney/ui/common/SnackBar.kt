package com.task.vpdmoney.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.task.vpdmoney.ui.theme.colorBackgroundWhite

@Composable
fun AppSnackBar(
    modifier: Modifier = Modifier,
    snackbarData: SnackbarData,
    shape: Shape = SnackbarDefaults.shape,
    containerColor: Color = SnackbarDefaults.color,
    contentColor: Color = SnackbarDefaults.contentColor,
    actionContentColor: Color = SnackbarDefaults.actionContentColor,
    dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor,
) {
    Snackbar(
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        actionContentColor = actionContentColor,
        dismissActionContentColor = dismissActionContentColor,
    ) {
        Text(
            text = snackbarData.visuals.message,
            color = colorBackgroundWhite,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            maxLines = Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun ShowAppSnackbar(
    snackbarHostState: SnackbarHostState,
    snackbarMessage: () -> String,
) {
    LaunchedEffect(key1 = snackbarMessage()) {
        if (snackbarMessage().isNotEmpty()) {
            snackbarHostState.showSnackbar(
                message = snackbarMessage(),
                duration = SnackbarDuration.Short,
            )
        }
    }
}