package com.example.movieinfotest.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {
    @Test
    fun getYear_1999_year() {
        val res = "1999 aug 13 sad ".getYear()
        assertEquals(res, "1999")
    }

    @Test
    fun getYear_199_year() {
        val res = "199".getYear()
        assertEquals(res, "Unknown Year")
    }

    @Test
    fun getYear_empty() {
        val res = (null as String?).getYear()
        assertEquals(res, "Unknown Year")
    }
}
