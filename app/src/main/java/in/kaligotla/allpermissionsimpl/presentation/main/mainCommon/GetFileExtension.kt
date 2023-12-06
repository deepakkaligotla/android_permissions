package `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon

import java.io.File
import java.util.Locale

fun getFileExtension(filePath: String): String {
    val file = File(filePath)
    val fileName = file.name

    val dotIndex = fileName.lastIndexOf('.')

    return if (dotIndex > 0 && dotIndex < fileName.length - 1) {
        fileName.substring(dotIndex + 1).lowercase(Locale.getDefault())
    } else {
        ""
    }
}