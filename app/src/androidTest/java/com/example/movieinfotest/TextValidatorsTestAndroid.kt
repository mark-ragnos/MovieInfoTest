package com.example.movieinfotest

import com.example.movieinfotest.utils.isCorrectEmail
import junit.framework.TestCase.assertEquals
import org.junit.Test

class TextValidatorsTestAndroid {

    //Email
    @Test
    fun isCorrectEmailTestWork() {
        val email = "example@example.com"
        assertEquals(isCorrectEmail(email), true)
    }

    @Test
    fun isCorrectEmailTestWithoutDot() {
        val email = "example@examplecom"
        assertEquals(isCorrectEmail(email), false)
    }

    @Test
    fun isCorrectEmailTestBeforeEmail() {
        val email = "example"
        assertEquals(isCorrectEmail(email), false)
    }

    @Test
    fun isCorrectEmailTestEmpty() {
        val email = ""
        assertEquals(isCorrectEmail(email), false)
    }
}
