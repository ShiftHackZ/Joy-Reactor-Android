package com.shifthackz.joyreactor.presentation.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun Bitmap.save(context: Context): File {
    val cacheDirectory = File("${context.cacheDir}/images")
    if (!cacheDirectory.exists()) cacheDirectory.mkdirs()
    val outFile = File(cacheDirectory, "${System.currentTimeMillis()}.jpg")
    outFile.writeBitmap(this)
    return outFile
}

fun File.writeBitmap(
    bitmap: Bitmap,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 100,
) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}

fun Context.uriFromFile(file: File, fileProviderPath: String): Uri {
    return FileProvider.getUriForFile(this, fileProviderPath, file)
}

fun Context.shareFile(
    file: File,
    fileProviderPath: String,
    fileMimeType: String? = null,
    shareChooserTitle: String? = null,
) {
    val uri = uriFromFile(file, fileProviderPath)
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        fileMimeType?.let { type = it }
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        putExtra(Intent.EXTRA_STREAM, uri)
    }
    startActivity(Intent.createChooser(shareIntent, shareChooserTitle))
}
