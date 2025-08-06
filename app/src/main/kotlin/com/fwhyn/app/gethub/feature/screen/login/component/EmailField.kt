package com.fwhyn.app.gethub.feature.screen.login.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    param: EmailFieldParam,
) {

    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "PersonIcon",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    TextField(
        modifier = modifier,
        value = param.value,
        onValueChange = param.onValueChange,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        label = { Text(param.label) },
        placeholder = { Text(param.placeholder) },
        singleLine = true,
    )
}

data class EmailFieldParam(
    val value: String,
    val onValueChange: (String) -> Unit,
    val label: String,
    val placeholder: String,
)

@Composable
fun getStateOfEmailField(
    value: StateFlow<String>,
    onValueChange: (String) -> Unit,
    label: String = stringResource(R.string.email),
    placeholder: String = stringResource(R.string.enter_your_email),
): EmailFieldParam {
    val emailValue: String by value.collectAsStateWithLifecycle()

    return EmailFieldParam(
        value = emailValue,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder
    )
}

@Preview
@Composable
fun EmailFieldPreview() {
    val emailState = MutableStateFlow("")

    MyTheme {
        EmailField(
            param = getStateOfEmailField(
                value = emailState,
                onValueChange = { emailState.value = it },
            )
        )
    }
}