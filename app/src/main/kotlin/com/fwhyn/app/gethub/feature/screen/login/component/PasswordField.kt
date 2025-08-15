package com.fwhyn.app.gethub.feature.screen.login.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    param: PasswordFieldParam,
) {

    var isPasswordVisible: Boolean by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Key,
            contentDescription = stringResource(R.string.password_icon),
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = stringResource(R.string.password_hide_unhide),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

    TextField(
        modifier = modifier,
        value = param.value,
        onValueChange = param.onValueChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        label = { Text(param.label) },
        placeholder = { Text(param.placeholder) },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )
    )
}

data class PasswordFieldParam(
    val value: String,
    val onValueChange: (String) -> Unit,
    val label: String,
    val placeholder: String,
)

@Composable
fun getStateOfPasswordField(
    value: StateFlow<String>,
    onValueChange: (String) -> Unit,
    label: String = stringResource(R.string.password),
    placeholder: String = stringResource(R.string.enter_your_password),
): PasswordFieldParam {
    val passwordValue: String by value.collectAsStateWithLifecycle()

    return PasswordFieldParam(
        value = passwordValue,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder
    )
}

@Preview
@Composable
fun PasswordFieldPreview() {
    val emailValue = MutableStateFlow("")

    MyTheme {
        PasswordField(
            param = getStateOfPasswordField(
                value = emailValue,
                onValueChange = {
                    emailValue.value = it
                }
            )
        )
    }
}