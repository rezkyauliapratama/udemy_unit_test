package android.rezkyauliapratama.com.automationtest

import android.rezkyauliapratama.com.automationtest.example1.PositiveNumberValidator
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PositiveNumberValidatorTest{

    lateinit var SUT : PositiveNumberValidator

    @Before
    fun setUp(){
        SUT = PositiveNumberValidator()

    }

    @Test
    fun test1(){
        val result = SUT.isPositive(-1)
        Assert.assertThat(result, CoreMatchers.`is`(false))
    }

    @Test
    fun test2(){
        val result = SUT.isPositive(0)
        Assert.assertThat(result,CoreMatchers.`is`(false))
    }

    @Test
    fun test3(){
        val result = SUT.isPositive(1)
        Assert.assertThat(result,CoreMatchers.`is`(true))
    }
}