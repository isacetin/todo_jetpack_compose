package com.example.my_application_todo.scene.home.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_application_todo.ui.theme.KoyuYesil

@Composable
fun TodoTaskItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    title: String,
    onNavigationToDetail: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .clickable { onNavigationToDetail.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            colors = CheckboxDefaults.colors(KoyuYesil),
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = title,
            style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 20.sp),
            textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
        )
    }
}


@Composable
@Preview(name = "Checked", showBackground = true)
private fun TodoTaskItemEnablePreview() {
    TodoTaskItem(true, {}, "Learn Compose", {})
}

@Composable
@Preview(name = "NoChecked", showBackground = true)
private fun TodoTaskItemDisablePreview() {
    TodoTaskItem(false, {}, "Learn Compose", {})
}