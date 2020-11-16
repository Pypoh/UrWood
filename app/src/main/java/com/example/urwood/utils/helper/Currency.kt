package com.example.urwood.utils.helper

import java.text.NumberFormat
import java.util.*

object Currency {

    fun intToRupiah(value: Int) : String {
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        formatRupiah.maximumFractionDigits = 0
        return formatRupiah.format(value.toDouble())
    }

}