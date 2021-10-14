package interaction

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon

fun responseToTagsCount(response: String) : VkResponse {
        return runCatching{
            Klaxon().parse<HashTagCntResponse>(response)!!
        }.recoverCatching{
            Klaxon().parse<ErrorResponse>(response)!!}.getOrNull() ?: throw RuntimeException("Error in json: $response")
}

open class VkResponse

data class HashTagCntResponse(val response: Count): VkResponse() {
    data class Count(@Json(name = "total_count") val postCount: Int)
}

data class ErrorResponse(val error: CodeAndMessage): VkResponse() {
    data class CodeAndMessage(
        @Json(name = "error_code") val errorCode: Int,
        @Json(name = "error_msg") val errorMsg: String
    )
}