package android.rezkyauliapratama.com.automationtest.example3

class IntervalsOverlapDetector{

    fun isOverlap(interval1: Interval, interval2: Interval): Boolean {
        return interval1.mEnd > interval2.mStart && interval1.mStart < interval2.mEnd
    }
}