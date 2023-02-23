package com.yxf.vehiclehj

import com.yxf.vehiclehj.utils.date2String
import org.junit.Test

import org.junit.Assert.*
import java.util.Date
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val date = Date()
        println(date2String(date,"HHmmss"))
    }
}