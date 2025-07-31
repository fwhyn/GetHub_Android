package com.fwhyn.app.gethub.feature.screen.login.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LabeledCheckbox(
    modifier: Modifier = Modifier,
    param: LabeledCheckboxParam,
) {

    Row(
        modifier
            .clickable(
                onClick = param.onCheckChanged
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = param.isChecked, onCheckedChange = null)
        MySpacer(6.dp)
        Text(param.label)
    }
}

data class LabeledCheckboxParam(
    val label: String,
    val onCheckChanged: () -> Unit,
    val isChecked: Boolean,
)

@Composable
fun getStateOfLabeledCheckbox(
    label: String,
    onCheckChanged: () -> Unit,
    isChecked: StateFlow<Boolean>,
): LabeledCheckboxParam {
    val checked: Boolean by isChecked.collectAsStateWithLifecycle()

    return LabeledCheckboxParam(
        label = label,
        onCheckChanged = onCheckChanged,
        isChecked = checked
    )
}

@Preview
@Composable
fun LabeledCheckboxPreview() {
    val isChecked = MutableStateFlow(false)

    MyTheme {
        LabeledCheckbox(
            param = getStateOfLabeledCheckbox(
                label = "Accept Terms and Conditions",
                onCheckChanged = {
                    isChecked.value = !isChecked.value
                },
                isChecked = isChecked
            )
        )
    }
}