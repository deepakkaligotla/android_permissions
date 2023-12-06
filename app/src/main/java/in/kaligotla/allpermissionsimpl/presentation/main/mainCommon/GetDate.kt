package `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon

import java.text.SimpleDateFormat
import java.util.Date

fun getDate(temp: Long?): String {
    return if(temp!=null) {
        SimpleDateFormat("dd").format(Date(temp)) + getExt(SimpleDateFormat("dd").format(Date(temp)).toInt()) + " " + SimpleDateFormat("MMM, yy").format(Date(temp))
    } else SimpleDateFormat("dd").format(Date()) + getExt(SimpleDateFormat("dd").format(Date()).toInt()) + " " + SimpleDateFormat("MMM, yy").format(Date())
}

fun getDateTime(temp: Long?): String {
    return if(temp!=null) {
        SimpleDateFormat("dd").format(Date(temp)) + getExt(SimpleDateFormat("dd").format(Date(temp)).toInt()) + "_" + SimpleDateFormat("MMM_yyyy_hh:mm:ss").format(Date(temp))
    } else SimpleDateFormat("dd").format(Date()) + getExt(SimpleDateFormat("dd").format(Date()).toInt()) + "_" + SimpleDateFormat("MMM_yyyy_hh:mm:ss").format(Date())
}

fun getExt(temp: Int): String {
    return if (temp in 11..13) {
        "th";
    } else when (temp % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}