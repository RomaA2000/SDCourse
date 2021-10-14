package counter

import interaction.*
import io.mockk.every
import io.mockk.mockk

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions

class HashTagCounterTest {
    @Test
    fun singleHashTagCounter() {
        val answerOne = HashTagCntResponse(HashTagCntResponse.Count(1))
        val client = mockk<VkClient> {
            every { getNewsCount(eq("hello"), any(), any()) } returns answerOne
        }
        val counter = HashTagCounter(client)
        Assertions.assertEquals(counter.count("hello", 1), listOf(1))
    }

    @Test
    fun multiHashTagCounter() {
        val answerOne = HashTagCntResponse(HashTagCntResponse.Count(1))
        val answerTwo = HashTagCntResponse(HashTagCntResponse.Count(2))
        val answerThree = HashTagCntResponse(HashTagCntResponse.Count(3))

        val client1 = mockk<VkClient> {
            every { getNewsCount(eq("hello1"), any(), any()) } returns answerOne
        }
        val client2 = mockk<VkClient> {
            every { getNewsCount(eq("hello2"), any(), any()) } returns answerTwo
        }
        val client3 = mockk<VkClient> {
            every { getNewsCount(eq("hello3"), any(), any()) } returns answerThree
        }

        val counter1 = HashTagCounter(client1)
        val counter2 = HashTagCounter(client2)
        val counter3 = HashTagCounter(client3)

        Assertions.assertEquals(counter1.count("hello1", 1), listOf(1))
        Assertions.assertEquals(counter2.count("hello2", 2), listOf(2, 2))
        Assertions.assertEquals(counter3.count("hello3", 3), listOf(3, 3, 3))
    }
}