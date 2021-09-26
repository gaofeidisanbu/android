package com.example.lib

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sun.misc.Unsafe
import java.util.*

object TempTest : Runnable {

    private final val  m = "aaa"

    override fun run() {
        val time = GregorianCalendar()
        time.time = Date()
        val year = time.get(GregorianCalendar.YEAR)
        val month = time.get(GregorianCalendar.MONTH)
        val day = time.get(GregorianCalendar.DAY_OF_MONTH)
        val value = "$year $month $day"
        System.out.println(value)

    }




}