
import junit.framework.Assert.assertEquals
import org.junit.Test
import statistic.EventStatisticImpl
import timer.ClockImpl
import timer.Timer
import java.time.Duration
import java.time.Instant

private const val MIN_PER_HOUR = 60

class EventStatisticTest {

    @Test
    fun test_simple1() {
        val eventStatistic = EventStatisticImpl(ClockImpl())
        eventStatistic.incEvent("1")
        eventStatistic.incEvent("2")
        eventStatistic.incEvent("2")
        eventStatistic.incEvent("1")
        assertEquals(
            eventStatistic.getAllEventStatistic(), mapOf(
                Pair("1", 2.0 / MIN_PER_HOUR),
                Pair("2", 2.0 / MIN_PER_HOUR)
            )
        )
    }

    @Test
    fun test_simple2() {
        val eventStatistic = EventStatisticImpl(ClockImpl())
        eventStatistic.incEvent("1")
        eventStatistic.incEvent("2")
        eventStatistic.incEvent("1")
        assertEquals(2.0 / MIN_PER_HOUR, eventStatistic.getEventStatisticByName("1"))
        assertEquals(1.0 / MIN_PER_HOUR, eventStatistic.getEventStatisticByName("2"))
        assertEquals(0.0, eventStatistic.getEventStatisticByName("3"))
    }

    @Test
    fun test_simple3() {
        val timer = Timer(Instant.now())
        val eventStatistic = EventStatisticImpl(timer)
        eventStatistic.incEvent("1")
        eventStatistic.incEvent("1")
        timer.add(Duration.ofMinutes(100))
        eventStatistic.incEvent("2")
        eventStatistic.incEvent("2")
        assertEquals(2.0 / MIN_PER_HOUR, eventStatistic.getEventStatisticByName("2"))
        assertEquals(0.0, eventStatistic.getEventStatisticByName("1"))
        assertEquals(
            eventStatistic.getAllEventStatistic(), mapOf(
                Pair("2", 2.0 / MIN_PER_HOUR),
                Pair("1", 0.0)
            )
        )
    }

    @Test
    fun test_hard3() {
        val timer = Timer(Instant.now())
        val eventStatistic = EventStatisticImpl(timer)
        eventStatistic.incEvent("1")
        eventStatistic.incEvent("1")
        eventStatistic.incEvent("2")
        timer.add(Duration.ofMinutes(30))
        assertEquals(
            eventStatistic.getAllEventStatistic(), mapOf(
                Pair("1", 2.0 / MIN_PER_HOUR),
                Pair("2", 1.0 / MIN_PER_HOUR)
            )
        )
        eventStatistic.incEvent("3")
        timer.add(Duration.ofMinutes(30))
        assertEquals(
            eventStatistic.getAllEventStatistic(), mapOf(
                Pair("1", 2.0 / MIN_PER_HOUR),
                Pair("2", 1.0 / MIN_PER_HOUR),
                Pair("3", 1.0 / MIN_PER_HOUR)
            )
        )
        timer.add(Duration.ofMinutes(15))
        assertEquals(
            eventStatistic.getAllEventStatistic(), mapOf(
                Pair("1", 0.0),
                Pair("2", 0.0),
                Pair("3", 1.0 / MIN_PER_HOUR)
            )
        )
    }
}