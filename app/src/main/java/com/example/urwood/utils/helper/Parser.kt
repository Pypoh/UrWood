package com.example.urwood.utils.helper

import java.text.SimpleDateFormat
import java.util.*

object Parser {

    fun simpleDateFormatFromDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyy")
        return dateFormat.format(date)
    }

    fun formatProfileName(name: String): String {
        var formattedName = ""
        val stringArray = name.split(" ").toTypedArray()
        for (string in stringArray) {
            formattedName += string.first()
        }
        return formattedName
    }
}