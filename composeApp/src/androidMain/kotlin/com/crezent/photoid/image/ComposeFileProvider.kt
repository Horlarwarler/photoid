package com.crezent.photoid.image

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.crezent.photoid.R
import java.io.File
import com.crezent.photoid.R.xml
import java.util.Objects

class ComposeFileProvider(
) : FileProvider(
    xml.file_paths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val tempFile = File.createTempFile(
                "temp_image",
                ".jpg",
                context.cacheDir
            ).apply {
                createNewFile()
            }
            val authority = "${context.packageName}.provider"
            return getUriForFile(
                Objects.requireNonNull(context),
                authority,
                tempFile
            )
        }
    }
}
///recussion to find the sum of odd numbers from 1 to n, using normal version to memoized version