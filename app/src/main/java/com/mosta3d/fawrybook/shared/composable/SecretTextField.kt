package com.mosta3d.fawrybook.shared.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.mosta3d.fawrybook.shared.form.AppFieldData

@Composable
fun AppSecretField(
    name: String,
    @StringRes labelId: Int,
    placeholder: Int? = null,
    fieldData: AppFieldData<String>,
    onChange: (AppFieldData<String>) -> Unit
) {
    val showSecretState = remember { mutableStateOf(false) }
    val showSecret by showSecretState

    Box {
        OutlinedTextField(
            label = { Text(text = stringResource(labelId)) },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (!it.isFocused) return@onFocusChanged
                    onChange(
                        fieldData.copy(
                            touched = true
                        )
                    )
                },
            placeholder = { Text(text = stringResource(placeholder ?: labelId)) },
            value = fieldData.value,
            onValueChange = {
                onChange(
                    fieldData.copy(
                        value = it
                    )
                )
            },
            isError = fieldData.touched && !fieldData.isValid,
            supportingText = {
                if (fieldData.touched && !fieldData.isValid && fieldData.errorMessages.isNotEmpty())
                    Text(
                        color = MaterialTheme.colorScheme.error,
                        text = stringResource(fieldData.errorMessages[0])
                    )
            },
            visualTransformation = if (showSecret) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier.clickable { showSecretState.value = !showSecret }
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
    }
}