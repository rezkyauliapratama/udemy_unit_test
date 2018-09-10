package android.rezkyauliapratama.com.automationtest.example2

import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class StringReverserTest{
    lateinit var SUT : StringReverser

    @Before
    fun setUp() {
        SUT = StringReverser()
    }

    @Test
    fun reverse_emptyString_emptyStringReturned() {
        val result = SUT.reverse("")

        assertThat(result, `is`(""))
    }

    @Test
    fun reverse_singleCharacter_sameStringReturned() {
        val result = SUT.reverse("a")
        assertThat(result, `is`("a"))
    }

    @Test
    fun reverse_longString_reversedStringReturned() {
        val result = SUT.reverse("Rezky Aulia")
        assertThat(result, `is`("ailuA ykzeR"))
    }
}