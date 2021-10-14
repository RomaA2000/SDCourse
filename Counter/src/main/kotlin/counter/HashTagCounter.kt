package counter

import interaction.HashTagCntResponse
import interaction.VkClient
import java.time.Duration
import java.time.Instant
import java.util.*

open class HashTagCounter(private val client: VkClient) {
    fun count(tag: String, number: Int) : List<Int> {
        val now = Instant.now().epochSecond
        val chart = ArrayList<Int>()
        var endTime = now
        val interval = Duration.ofHours(1).seconds
        var startTime = endTime - interval
        for (i in 1..number) {
            val data = client.getNewsCount(tag, startTime, endTime)
            chart.add(when (data) {
                is HashTagCntResponse -> data.response.postCount
                else -> -1
            })
            endTime -= interval
            startTime -= interval
        }
        return chart
    }
}