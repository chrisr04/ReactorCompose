package com.animotionsoftware.reactor.example.ui.common.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutlinedInput(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(4.dp),
        textStyle = TextStyle(fontSize = 14.sp),
        keyboardOptions = keyboardOptions,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 14.sp,
                lineHeight = 1.sp
            )
        },
        trailingIcon = trailingIcon,
        enabled = enabled,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
    )
}