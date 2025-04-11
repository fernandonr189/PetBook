package com.example.petbook.util

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

fun bitmapToBase64(bitmap: Bitmap?): String {
    if(bitmap == null) {
        return ""
    }
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream) // or JPEG
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}