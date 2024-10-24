package presentation.editor.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import domain.model.Uniform
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.children
import photoid.composeapp.generated.resources.men
import photoid.composeapp.generated.resources.none
import photoid.composeapp.generated.resources.women
import ui.light_grey
import ui.mediumPadding
import ui.purple
import ui.smallPadding
import ui.white


@Composable
fun UniformSelector(
    modifier: Modifier,
    selectedCategory: Int = 0,
    selectedUniformId: String? = null,
    onCategorySelected: (Int) -> Unit,
    onUniformSelected: (String?) -> Unit,
    uniforms: List<Uniform>
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(
                    white
                )
                .padding(
                    5.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CategorySelector(
                text = Res.string.men,
                isSelected = selectedCategory == 0,
                onClick = {
                    onCategorySelected(0)
                }
            )
            CategorySelector(
                text = Res.string.women,
                isSelected = selectedCategory == 1,
                onClick = {
                    onCategorySelected(1)
                }
            )
            CategorySelector(
                text = Res.string.children,
                isSelected = selectedCategory == 2,
                onClick = {
                    onCategorySelected(2)
                }
            )
        }
        Spacer(Modifier.height(mediumPadding))
        LazyRow {
            item {
                ApplyNone(
                    onClick = { onUniformSelected(null) },
                    isSelected = selectedUniformId == null
                )
            }
            items(uniforms) { uniform ->
                UniformItem(
                    uniform = uniform,
                    isSelected = uniform.uniformId == selectedUniformId,
                    onClick = {
                        onUniformSelected(uniform.uniformId)
                    }
                )
            }
        }
    }
}

@Composable
private fun CategorySelector(
    text: StringResource,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = if (isSelected) purple else Color.Transparent
            )
            .padding(
                horizontal = 10.dp, vertical = 4.dp
            ),
        text = stringResource(text),
        color = if (isSelected) white else light_grey,
        fontSize = 12.sp
    )
}

@Composable
private fun UniformItem(
    uniform: Uniform,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var isLoading by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .padding(
                end = smallPadding
            )
            .clickable {
                onClick()
            }
            .border(
                width = 1.dp,
                color = if (isSelected) purple else Color.Transparent,
                shape = RoundedCornerShape(15.dp)
            )
            .size(height = 70.dp, width = 50.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(white),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp),
                color = purple
            )
        }

        AsyncImage(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(50.dp)
                .fillMaxWidth(),
            model = uniform.uniformUrl,
            contentDescription = "uniform",
            onLoading = {
                isLoading = true
            },
            onSuccess = {
                isLoading = false
            },
            onError = {
                // isLoading = false
            },
            contentScale = ContentScale.FillBounds
        )

    }
}