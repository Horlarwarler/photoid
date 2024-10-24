import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.smallPadding

@Composable
fun CustomTextButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit,
    buttonColor: Color,
    backgroundColor: Color,
    isEnabled: Boolean = true

) {
    TextButton(

        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = isEnabled,
        shape = RoundedCornerShape(14.dp),
        colors = androidx.compose.material.ButtonDefaults.textButtonColors(
            contentColor = buttonColor,
            backgroundColor = backgroundColor
        ),

        ) {
        Text(
            modifier = Modifier.padding(
                vertical = 5.dp
            ),
            text = buttonText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }

}