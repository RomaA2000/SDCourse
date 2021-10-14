package interaction

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ResponseToTagsCountTest {
    @Test
    fun simpleParse() {
        Assertions.assertEquals(HashTagCntResponse(HashTagCntResponse.Count(0)), responseToTagsCount("{\"response\" : { \"total_count\": 0}}"))
        Assertions.assertEquals(HashTagCntResponse(HashTagCntResponse.Count(3)), responseToTagsCount("{\"response\" : { \"total_count\": 3}}"))
        Assertions.assertEquals(HashTagCntResponse(HashTagCntResponse.Count(-2)), responseToTagsCount("{\"response\" : { \"total_count\": -2}}"))
        Assertions.assertEquals(HashTagCntResponse(HashTagCntResponse.Count(103)), responseToTagsCount("{\"response\" : { \"total_count\": 103}}"))
    }

    @Test
    fun errorParse() {
        Assertions.assertEquals(ErrorResponse(ErrorResponse.CodeAndMessage(1, "something")), responseToTagsCount("""{"error":{"error_code":1, "error_msg": "something"}}"""))
        Assertions.assertEquals(ErrorResponse(ErrorResponse.CodeAndMessage(1212, "")), responseToTagsCount("""{"error":{"error_code":1212, "error_msg": ""}}"""))
    }

    @Test
    fun badResponseParse() {
        Assertions.assertThrows(RuntimeException::class.java) {
            responseToTagsCount("adsadadasd")
        }

        Assertions.assertThrows(RuntimeException::class.java) {
            responseToTagsCount("")
        }
    }
}