package com.example.movieinfotest.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class TextValidatorsTest {

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

    //Email input
    @Test
    fun isCorrectEmailInputTestEmpty() {
        val email = ""
        assertEquals(isCorrectEmailInput(email), true)
    }

    @Test
    fun isCorrectEmailInputTestCorrectLength() {
        val email = "sdadonq9nqwwq0nwq@0n9das9"
        assertEquals(isCorrectEmailInput(email), true)
    }

    @Test
    fun isCorrectEmailInputTestTooLong() {
        val chars = CharArray(size = 300)
        val email = String(chars)
        assertEquals(isCorrectEmailInput(email), false)
    }

    //Password
    @Test
    fun isCorrectPasswordTestCorrect() {
        val password = "sanidsaiddnq-dqwm12x"
        assertEquals(isCorrectPassword(password), true)
    }

    @Test
    fun isCorrectPasswordTestTooShort() {
        val password = "sani"
        assertEquals(isCorrectPassword(password), false)
    }

    @Test
    fun isCorrectPasswordTestTooLong() {
        val password =
            "sanidsaiddsadn9qw9qw-wn9qw9qq8qd89qwn8wd8q8-wq89wqn89wq9wqnwqqw8wq8nwqn88wqn98wq-wq89wq89qwn8w8q9-dnq-dqwm12x"
        assertEquals(isCorrectPassword(password), false)
    }

    @Test
    fun isCorrectPasswordTestEmpty() {
        val password = ""
        assertEquals(isCorrectPassword(password), false)
    }

    @Test
    fun isCorrectPasswordTest8Lenght() {
        val password = "12345678"
        assertEquals(isCorrectPassword(password), true)
    }

    //Password Input
    @Test
    fun isCorrectPasswordInputTest8Length() {
        val password = "12345678"
        assertEquals(isCorrectPasswordInput(password), true)
    }

    @Test
    fun isCorrectPasswordInputTest32Length() {
        val password = "01234567890123456789012345678912"
        assertEquals(isCorrectPasswordInput(password), true)
    }

    @Test
    fun isCorrectPasswordInputTest33Length() {
        val password = "012345678901234567890123456789123"
        assertEquals(isCorrectPasswordInput(password), false)
    }

    @Test
    fun isCorrectPasswordInputTestEmpty() {
        val password = ""
        assertEquals(isCorrectPasswordInput(password), true)
    }

    //Year
    @Test
    fun isCorrectYearTestCorrect() {
        val year = "1999"
        assertEquals(isCorrectYear(year), true)
    }

    @Test
    fun isCorrectYearTestMinYear() {
        val year = "1895"
        assertEquals(isCorrectYear(year), true)
    }

    @Test
    fun isCorrectYearTestMaxYear() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val year = currentYear.toString()
        assertEquals(isCorrectYear(year), true)
    }

    @Test
    fun isCorrectYearTestIncorrectFormat() {
        val year = "1895e"
        assertEquals(isCorrectYear(year), false)
    }

    @Test
    fun isCorrectYearTestIncorrectYear() {
        val year = "1795"
        assertEquals(isCorrectYear(year), false)
    }

    @Test
    fun isCorrectYearTestNotMaxYearLength() {
        val year = "179"
        assertEquals(isCorrectYear(year), false)
    }

    @Test
    fun isCorrectYearTestEmpty() {
        val year = ""
        assertEquals(isCorrectYear(year), true)
    }

    //Year Input
    @Test
    fun isCorrectYearInputTestCorrect() {
        val year = "1790"
        assertEquals(isCorrectYearInput(year), true)
    }

    @Test
    fun isCorrectYearInputTestCorrect2() {
        val year = "17"
        assertEquals(isCorrectYearInput(year), true)
    }

    @Test
    fun isCorrectYearInputTestIncorrect() {
        val year = "17900"
        assertEquals(isCorrectYearInput(year), false)
    }
}
