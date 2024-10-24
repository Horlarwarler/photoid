package presentation.specification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import domain.model.Specification
import domain.model.SpecificationType
import presentation.components.BackButton
import presentation.components.PhotoSpecificationItem
import presentation.specification.components.PhotoSpecificationTypeItem
import presentation.specification.components.SearchBar
import ui.background
import ui.black
import ui.largePadding
import ui.mediumPadding
import ui.purple
import ui.smallPadding
import ui.white

@Composable
fun SpecificationScreen(
    onBackClick: () -> Unit,
    specificationScreenState: SpecificationScreenState,
    onScreenEvent: (SpecificationScreenEvent) -> Unit,
    onSpecificationClick: (Specification) -> Unit,
) {
    val specificationTypes = listOf(
        SpecificationType.PASSPORT,
        SpecificationType.VISA,
        SpecificationType.CV_PHOTO,
        SpecificationType.CUSTOM,
    )
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .clickable {
                focusManager.clearFocus()
            }
            .fillMaxSize()
            .background(white)
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                )
                .height(200.dp)
                .background(background)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton(
                    backButtonClick = onBackClick
                )
                Spacer(Modifier.width(smallPadding))
                SearchBar(
                    value = specificationScreenState.searchText,
                    onValueChange = {
                        onScreenEvent(SpecificationScreenEvent.OnSearchChange(it))
                    },
                    focusManager = focusManager
                )
            }

        }

        Row(
            modifier = Modifier
                .offset(
                    y = (-80).dp
                )
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(start = mediumPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            specificationTypes.forEach {
                PhotoSpecificationTypeItem(
                    isSelected = it == specificationScreenState.selectedSpecificationType,
                    specificationType = it,
                    onClick = {
                        if (it == SpecificationType.CUSTOM) {
                            onScreenEvent(SpecificationScreenEvent.OnCustomSpecificationClick)
                        } else {
                            onScreenEvent(SpecificationScreenEvent.OnSpecificationTypeClick(it))
                        }
                    }
                )
            }
        }

        if (specificationScreenState.isLoading) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = purple
                )
            }
            return@Column
        }
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = mediumPadding)
                .offset(y = (-50).dp)
        ) {
            items(specificationScreenState.filteredOrSortedSpecification) { specification ->
                PhotoSpecificationItem(
                    specification = specification,
                    onClick = {
                        onSpecificationClick(specification)
                    }
                )
            }
        }
    }
}