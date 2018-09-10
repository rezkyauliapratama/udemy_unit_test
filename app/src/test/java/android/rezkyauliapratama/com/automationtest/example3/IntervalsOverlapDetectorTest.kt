package android.rezkyauliapratama.com.automationtest.example3

import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class IntervalsOverlapDetectorTest{

    lateinit var SUT : IntervalsOverlapDetector

    @Before
    fun setUp() {
        SUT = IntervalsOverlapDetector()
    }

    @Test
    @Throws(Exception::class)
    fun isOverlap_interval1BeforeInterval2_falseReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(8, 12)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, `is`(false))
    }

    // interval1 overlaps interval2 on start

    @Test
    @Throws(Exception::class)
    fun isOverlap_interval1OverlapsInterval2OnStart_trueReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(3, 12)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, `is`(true))
    }
    // interval1 is contained within interval2

    @Test
    @Throws(Exception::class)
    fun isOverlap_interval1ContainedWithinInterval2_trueReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(-4, 12)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, `is`(true))
    }
    // interval1 contains interval2

    @Test
    @Throws(Exception::class)
    fun isOverlap_interval1ContainsInterval2_trueReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(0, 3)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, `is`(true))
    }
    // interval1 overlaps interval2 on end

    @Test
    @Throws(Exception::class)
    fun isOverlap_interval1OverlapsInterval2OnEnd_trueReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(-4, 4)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, `is`(true))
    }
    // interval1 is after interval2

    @Test
    @Throws(Exception::class)
    fun isOverlap_interval1AfterInterval2_falseReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(-10, -3)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, `is`(false))
    }

    @Test
    @Throws(Exception::class)
    fun isOverlap_interval1BeforeAdjacentInterval2_falseReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(5, 8)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, `is`(false))
    }

    @Test
    @Throws(Exception::class)
    fun isOverlap_interval1AfterAdjacentInterval2_falseReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(-3, -1)
        val result = SUT.isOverlap(interval1, interval2)
        assertThat(result, `is`(false))
    }
}