package com.task.vpdmoney.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.vpdmoney.R
import com.task.vpdmoney.ui.theme.Purple80
import com.task.vpdmoney.ui.theme.colorTextBlack
import com.task.vpdmoney.ui.theme.colorTextLightBlack

@Composable
fun AppOutlinedTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    placeholder: String = "",
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = colorTextBlack,
    placeHolderColor: Color = colorTextLightBlack,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLength: Int = Int.MAX_VALUE,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isTextSetManually: Boolean = false,
    onValueChange: (String) -> Unit = {},
) {
    var textValue by remember { mutableStateOf(text) }

    Box(
        modifier = modifier
            .border(
                width = 0.1.dp,
                color = colorTextBlack,
                shape = RoundedCornerShape(size = 3.dp),
            )
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = if (isTextSetManually) text else textValue,
            onValueChange = {
                if (it.length <= maxLength) {
                    textValue = it
                    onValueChange(it)
                } else {
                    textValue = it.take(maxLength)
                    onValueChange(textValue)
                }
            },
            textStyle = textStyle.copy(
                color = textColor,
                textAlign = textAlign,
                fontSize = fontSize,
                fontWeight = fontWeight,
            ),
            cursorBrush = SolidColor(colorTextBlack),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            readOnly = readOnly,
            enabled = enabled,
            visualTransformation = visualTransformation,
            decorationBox = { innerTextField ->
                val finalValue = if (isTextSetManually) text else textValue
                if (finalValue.isEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = placeholder,
                        style = textStyle,
                        fontWeight = fontWeight,
                        fontSize = fontSize,
                        color = placeHolderColor,
                        textAlign = textAlign,
                        maxLines = maxLines,
                        minLines = minLines,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                innerTextField()
            },
        )
    }
}

@Composable
fun AppOutlinedPasswordTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    placeholder: String = "",
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = colorTextBlack,
    placeHolderColor: Color = colorTextLightBlack,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    maxLength: Int = Int.MAX_VALUE,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Password,
    imeAction: ImeAction = ImeAction.Next,
    isTextSetManually: Boolean = false,
    onValueChange: (String) -> Unit = {},
) {
    var textValue by remember { mutableStateOf(text) }
    var showPassword by remember { mutableStateOf(false) }

    val icon = if (showPassword) {
        painterResource(id = R.drawable.ic_eye_filled)
    } else {
        painterResource(id = R.drawable.ic_eye_cross)
    }

    Box(
        modifier = modifier
            .border(
                width = 0.1.dp,
                color = colorTextBlack,
                shape = RoundedCornerShape(size = 3.dp),
            )
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = if (isTextSetManually) text else textValue,
            onValueChange = {
                if (it.length <= maxLength) {
                    textValue = it
                    onValueChange(it)
                } else {
                    textValue = it.take(maxLength)
                    onValueChange(textValue)
                }
            },
            textStyle = textStyle.copy(
                color = textColor,
                textAlign = textAlign,
                fontSize = fontSize,
                fontWeight = fontWeight,
            ),
            cursorBrush = SolidColor(colorTextBlack),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            readOnly = readOnly,
            enabled = enabled,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                    ) {
                        val finalValue = if (isTextSetManually) text else textValue
                        if (finalValue.isEmpty()) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = placeholder,
                                style = textStyle,
                                fontWeight = fontWeight,
                                fontSize = fontSize,
                                color = placeHolderColor,
                                textAlign = textAlign,
                                maxLines = maxLines,
                                minLines = minLines,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        innerTextField()
                    }

                    Icon(
                        modifier = Modifier
                            .size(15.dp)
                            .clickable { showPassword = !showPassword },
                        painter = icon,
                        tint = Purple80,
                        contentDescription = null,
                    )
                }
            },
        )
    }
}