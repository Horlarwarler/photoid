package presentation.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.continue_editing
import photoid.composeapp.generated.resources.leave_work
import photoid.composeapp.generated.resources.photo_editor
import photoid.composeapp.generated.resources.search_description
import photoid.composeapp.generated.resources.specification_details
import photoid.composeapp.generated.resources.unsaved_work
import photoid.composeapp.generated.resources.unsaved_work_description
import photoid.composeapp.generated.resources.uploading
import presentation.components.BackButton
import presentation.components.ConfirmationDialog
import presentation.components.PictureDisplay
import presentation.components.TextAppBar
import presentation.editor.composable.Beauty
import presentation.editor.composable.ColorSelector
import presentation.editor.composable.EditorNavigation
import presentation.editor.composable.UniformSelector
import ui.background
import ui.eighth_background
import ui.fifth_background
import ui.first_background
import ui.fourth_background
import ui.mediumPadding
import ui.ninth_background
import ui.second_background
import ui.seventh_background
import ui.sixth_background
import ui.smallPadding
import ui.third_background
import ui.white

@Composable
fun EditorScreen(
    editorScreenState: EditorScreenState,
    event: (EditorEvent) -> Unit,
    navigateBack: () -> Unit
) {
    var photoState by remember {
        mutableStateOf(PhotoState.INITIAL)
    }


    var showWarningDialog by remember {
        mutableStateOf(false)
    }
    val onBackClick: () -> Unit = {
        if (showWarningDialog) {
            navigateBack()
        } else {
            showWarningDialog = true
        }
    }

    var navigatorOption by remember {
        mutableStateOf(0)
    }


    val colors = listOf(
        first_background,
        second_background,
        third_background,
        fourth_background,
        fifth_background,
        sixth_background,
        seventh_background,
        eighth_background,
        ninth_background
    )

    var photoImageUrl by remember {
        mutableStateOf<String?>(null)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = white,
        topBar = {
            TextAppBar(
                modifier = Modifier.background(
                    background
                )
                    .padding(mediumPadding),
                text = stringResource(Res.string.photo_editor)
            ) {
                showWarningDialog = true
            }
        }
    ) {


        if (showWarningDialog) {
            ConfirmationDialog(
                modifier = Modifier,
                dialogTitle = Res.string.unsaved_work,
                dialogDescription = Res.string.unsaved_work_description,
                negativeButtonText = Res.string.leave_work,
                positiveButtonText = Res.string.continue_editing,
                onDismissDialog = {
                    showWarningDialog = false
                },
                onNegativeButtonClick = {
                    showWarningDialog = false
                    navigateBack()

                },
                onPositiveButtonClick = {
                    showWarningDialog = false
                }
            )
        }

        Column(
        ) {
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = mediumPadding,
                            bottomEnd = mediumPadding
                        )
                    )
                    .background(background)
                    .fillMaxWidth()
                    .padding(bottom = mediumPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(smallPadding)
            ) {

                Box(
                    modifier = Modifier.weight(0.7f),
                    contentAlignment = Alignment.Center
                ) {

                    if (editorScreenState.specification != null) {
                        PictureDisplay(
                            modifier = Modifier
                                .aspectRatio(0.9f, true)
                                .fillMaxSize(),
                            dpi = editorScreenState.specification.dpi,
                            specification = editorScreenState.specification,
                            imageUrl = editorScreenState.userPhoto?.file,
                            color = colors[editorScreenState.selectedColorIndex]
                        )
                    }

                }
                Spacer(
                    modifier = Modifier.height(smallPadding)
                )
                Box(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxWidth()
                ) {
                    when (navigatorOption) {
                        0 -> {
                            ColorSelector(
                                modifier = Modifier.align(
                                    Alignment.BottomCenter
                                ),
                                currentIndex = editorScreenState.selectedColorIndex,
                                onColorSelected = { newIndex ->
                                    event(EditorEvent.SetSelectedColorIndex(newIndex))
                                },
                                colors = colors
                            )
                        }

                        1 -> {
                            UniformSelector(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(start = mediumPadding),
                                selectedCategory = editorScreenState.selectedUniformCategory,
                                onCategorySelected = {
                                    event(EditorEvent.SetSelectedUniformCategory(it))
                                },
                                onUniformSelected = {
                                    event(EditorEvent.SetSelectedUniform(it))
                                },
                                selectedUniformId = editorScreenState.selectedUniformId,
                                uniforms = editorScreenState.uniforms
                            )
                        }

                        2 -> {
                            Beauty(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(
                                        start = mediumPadding
                                    ),
                                isSelected = editorScreenState.beautifyImage,
                                onClick = {
                                    event(EditorEvent.SetBeautifyImage(it))
                                }
                            )
                        }

                        3 -> {

                        }
                    }

                }


                Spacer(modifier = Modifier.height(mediumPadding))

            }

            Spacer(modifier = Modifier.height(mediumPadding))

            Box(
                modifier = Modifier
                    .background(white)
                    .weight(0.2f)
                    .fillMaxWidth()
                    .padding(horizontal = mediumPadding),
                contentAlignment = Alignment.Center
            ) {
                EditorNavigation(
                    modifier = Modifier,
                    currentIndex = navigatorOption,
                    onClick = {
                        navigatorOption = it
                    }
                )

            }
        }

    }

}

enum class PhotoState {
    INITIAL,
    LOADING,
    LOADED
}