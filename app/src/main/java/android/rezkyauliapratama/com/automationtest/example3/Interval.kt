package android.rezkyauliapratama.com.automationtest.example3

data class Interval(
    var mStart: Int,
    var mEnd: Int

){
    init {
        if(mStart >= mEnd)
            throw IllegalArgumentException("Invalid interval range")
    }
}