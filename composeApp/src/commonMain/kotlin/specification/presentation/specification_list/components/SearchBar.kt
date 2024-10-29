package specification.presentation.specification_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.search
import photoid.composeapp.generated.resources.search_description
import ui.black
import ui.light_grey
import ui.white

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager
) {


    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,

        placeholder = {
            Text(
                text = stringResource(Res.string.search_description),
                color = light_grey,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        },
        textStyle = TextStyle(
            color = black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.search),
                contentDescription = null,
                tint = light_grey
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = white,
            cursorColor = light_grey,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = light_grey,
            placeholderColor = light_grey
        ),
        shape = RoundedCornerShape(200.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
            }
        )
    )


}