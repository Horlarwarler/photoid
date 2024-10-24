package presentation.specification_details

import CustomTextButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.ImageProcessState
import domain.model.Specification
import domain.model.UserPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.dpi
import photoid.composeapp.generated.resources.open_album
import photoid.composeapp.generated.resources.pixel
import photoid.composeapp.generated.resources.processing
import photoid.composeapp.generated.resources.size
import photoid.composeapp.generated.resources.specification_details
import photoid.composeapp.generated.resources.take_photo
import photoid.composeapp.generated.resources.uploading
import presentation.components.PictureDisplay
import presentation.components.TextAppBar
import presentation.specification_details.components.DpiChooser
import presentation.specification_details.components.ImageProcessingBox
import presentation.specification_details.components.SpecificationDetailsItem
import ui.background
import ui.black
import ui.largePadding
import ui.light_grey
import ui.mediumPadding
import ui.purple
import ui.smallPadding
import ui.white
import util.CameraPreview
import util.GoogleMlkit.faceDetected
import util.GoogleMlkit.getResult
import util.PermissionCallback
import util.PermissionStatus
import util.PermissionType
import util.UploadResult
import util.createPermissionManager
import util.rememberCameraManager
import util.rememberGalleryManager


@Composable
fun SpecificationDetailsScreen(
    specification: Specification,
    specificationDetailsState: SpecificationDetailsState,
    event: (SpecificationDetailsEvent) -> Unit,
    navigateToEditor: (UserPhoto, Specification) -> Unit,
    onBackClick: () -> Unit,
) {
    var launchCamera by remember {
        mutableStateOf(
            false
        )
    }
    //temp fix

    var launchGallery by remember {
        mutableStateOf(
            false
        )
    }
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    var launchNotification by remember {
        mutableStateOf(
            false
        )
    }
    var launchSettings by remember {
        mutableStateOf(
            false
        )
    }

    var previousAction by remember {
        mutableStateOf(PREVIOUS_ACTION.NONE)
    }
    var showPermissionRationale by remember {
        mutableStateOf(false)
    }

    var showPreview by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()


    val specificationInMm =
        "${specification.width}x${specification.height}mm"
    var selectedDpiIndex by remember {
        mutableStateOf(0)
    }
    val dpi by remember(selectedDpiIndex) {
        val dpiValueToInt = try {
            selectedDpiIndex.toDpiString().toInt()
        } catch (error: NumberFormatException) {
            1200
        }
        mutableStateOf(dpiValueToInt)
    }
    val specificationInPx by remember(dpi) {

        val specificationString =
            "${specification.toPx(dpi).first} x ${specification.toPx(dpi).second} px"
        mutableStateOf(specificationString)
    }
    val createPermissionManager = createPermissionManager(
        permissionCallback = object : PermissionCallback {
            override fun onPermissionStatus(status: PermissionStatus, permission: PermissionType) {
                when (status) {
                    PermissionStatus.GRANTED -> {
                        when (permission) {
                            PermissionType.CAMERA -> {
                                launchCamera = true
                            }

                            PermissionType.GALLERY -> {
                                launchGallery = true
                            }

                            PermissionType.NOTIFICATION -> {
                                launchNotification = true
                            }
                        }
                    }

                    else -> {
                        showPermissionRationale = true
                    }
                }

            }

        }
    )
    var showImageUploadDialog by remember {
        mutableStateOf(false)
    }
    var imageProcessState by remember {
        mutableStateOf(ImageProcessState.INITIAL)
    }
    var job: Job? = null

    fun processImage(imageToProcess: ImageBitmap) {
        coroutineScope.launch() {

            val faceFound = faceDetected(imageToProcess)
            if (!faceFound) {
                imageProcessState = ImageProcessState.ERROR

                println("IMAGE NOT FOUND")
                return@launch
            }

            println("IMAGE  FOUND")

            val processedImage = getResult(imageToProcess)

            if (processedImage == null) {
                imageProcessState = ImageProcessState.ERROR
                println("IMAGE NO PROCESSED FOUND")

                return@launch
            }
            println("IMAGE PROCESSED FOUND")

            showImageUploadDialog = false

            imageProcessState = ImageProcessState.PROCESSED
            imageBitmap = processedImage
            delay(1000)
            imageProcessState = ImageProcessState.INITIAL
        }
    }


    val cameraManager = rememberCameraManager(
        onPicTaken = { sharedImage ->
            event(
                SpecificationDetailsEvent.UploadImage(
                    sharedImage?.toByteArray()!!,
                    specification
                )
            )
            imageBitmap = sharedImage.toImageBitmap()
        }
    )


    val galleryManager = rememberGalleryManager(
        onImagePicked = { sharedImage ->
            showImageUploadDialog = true

            if (sharedImage != null) {
                job?.cancel()
                job = Job()
                coroutineScope.launch(Dispatchers.IO + job!!) {
                    // imageBitmap =

                    processImage(sharedImage.toImageBitmap()!!)
                }
            } else {
                imageProcessState = ImageProcessState.SELECTING_IMAGE

            }

            //
            //

//            event(
//                SpecificationDetailsEvent.UploadImage(
//                    sharedImage?.toByteArray()!!,
//                    specification
//                )
//            )

        }
    )


    var showEditDialog by remember {
        mutableStateOf(false)
    }




    if (showPreview) {
        CameraPreview(
            onPicTaken = { sharedImage ->
//                event(
//                    SpecificationDetailsEvent.UploadImage(
//                        sharedImage?.toByteArray()!!,
//                        specification
//                    )
//                )
                imageBitmap = sharedImage?.toImageBitmap()
                showPreview = false
            },
            onBackClick = {
                showPreview = false
            },
            onGalleryClick = {
                showPreview = false
                launchGallery = true
            }
        )
        return
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
                text = stringResource(Res.string.specification_details)
            ) {
                onBackClick()
            }
        }
    ) {

        if (showImageUploadDialog) {
            println("WILL SHOW IMAGE DIALOG")
            ImageProcessingBox(
                imageProcessState = imageProcessState,
                reUpload = {
                    if (previousAction == PREVIOUS_ACTION.CAMERA) {
                        launchCamera = true
                    } else if (previousAction == PREVIOUS_ACTION.GALLERY) {
                        launchGallery = true
                    }
                },
                dismissDialog = {
                    showImageUploadDialog = false
                }
            )
        }



        if (launchCamera) {
            if (createPermissionManager.isPermissionGranted(PermissionType.CAMERA)) {
                println("should launch camera")
                showPreview = true
                //cameraManager.launchCamera()
            } else {
                println("Will ask for permission")

                createPermissionManager.askForPermission(PermissionType.CAMERA)
            }
            launchCamera = false
            previousAction = PREVIOUS_ACTION.CAMERA
        }

        if (launchGallery) {
            if (createPermissionManager.isPermissionGranted(PermissionType.GALLERY)) {
                galleryManager.launch()
            } else {
                createPermissionManager.askForPermission(PermissionType.GALLERY)
            }
            launchGallery = false
            previousAction = PREVIOUS_ACTION.GALLERY

        }


        Column(
        ) {


            if (showEditDialog) {
                DpiChooser(
                    onDpiChoose = {
                        selectedDpiIndex = it
                        showEditDialog = false
                    },
                    cancelDialog = {
                        showEditDialog = false
                    },
                    selectedIndex = selectedDpiIndex
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.55f)
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
                PictureDisplay(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.8f, true)
                        .fillMaxWidth(),
                    dpi = dpi,
                    specification = specification,
                    imageUrl = null,
                    imageBitmap = imageBitmap,
                )
                Text(
                    text = specification.name.toUpperCase(),
                    color = black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(mediumPadding))
            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(
                        start = mediumPadding, end = mediumPadding,
                    )
            ) {
                SpecificationDetailsItem(
                    value = selectedDpiIndex.toDpiString(),
                    bottomText = stringResource(Res.string.dpi),
                    onEditClick = {
                        showEditDialog = true
                    }
                )
                SpecificationDetailsItem(
                    value = specificationInMm,
                    bottomText = stringResource(Res.string.size)
                )
                SpecificationDetailsItem(
                    value = specificationInPx,
                    bottomText = stringResource(Res.string.pixel)
                )

            }
            Box(
                modifier = Modifier
                    .weight(0.2f)
                    .offset(y = -smallPadding)
                    .fillMaxWidth()
                    .padding(horizontal = mediumPadding),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier.padding(bottom = mediumPadding),

                    ) {
                    CustomTextButton(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = light_grey,
                                shape = RoundedCornerShape(mediumPadding)
                            ),
                        buttonText = stringResource(Res.string.open_album),
                        buttonColor = light_grey,
                        backgroundColor = Color.Transparent,
                        onClick = {
                            launchGallery = true
                        }
                    )
                    Spacer(modifier = Modifier.width(smallPadding))
                    CustomTextButton(
                        modifier = Modifier.weight(1f),
                        buttonText = stringResource(Res.string.take_photo),
                        buttonColor = white,
                        backgroundColor = purple,
                        onClick = {
                            launchCamera = true
                        }
                    )
                }

            }
        }


    }
}

@Composable
private fun ImageUploading(
    uploadResult: UploadResult,
    imageBitmap: ImageBitmap
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            bitmap = imageBitmap,
            contentDescription = "Uploading Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(mediumPadding)),
            contentScale = ContentScale.FillBounds,
            alpha = 0.5f
            //colorFilter =
        )
        val textString = if (uploadResult is UploadResult.Uploading) {
            stringResource(Res.string.uploading) + " " + uploadResult.percent
            // "Uploading ${uploadResult.percent}"
            // stringResource(Res.string.uploading)
        } else {
            stringResource(Res.string.processing)
        }

        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(40.dp),
            color = purple
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(
                    y = -largePadding
                ),
            text = textString,
            color = purple,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,

            )
    }
}

private fun Int.toDpiString(): String {
    return when (this) {
        0 -> "300"
        1 -> "350"
        2 -> "450"
        3 -> "600"
        else -> "HD"
    }
}

enum class PREVIOUS_ACTION {
    NONE,
    CAMERA,
    GALLERY,
}

//private fun Int.toDpiValue():Int {
//    return when(this){
//        0 -> 300"
//        1 ->"350"
//        2 -> "450"
//        3 -> "600"
//        else -> "HD"
//    }
//}