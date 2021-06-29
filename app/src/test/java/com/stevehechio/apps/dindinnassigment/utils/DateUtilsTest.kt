package com.stevehechio.apps.dindinnassigment.utils

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by stevehechio on 6/29/21
 */
class DateUtilsTest {

    @Test
    fun formatDateStr() {
        val date = "2021-06-17T14:17:44+0300"
        val millis: Long = DateUtils.formatDateStr(date)
        assertEquals(1623928664000L,millis)
    }

    @Test
    fun formatGMTDateStr() {
        val date = "2021-06-17T14:17:44+0300"
        val dateString: String = DateUtils.formatGMTDateStr(date)
        assertEquals("02:17 PM",dateString)
    }
}