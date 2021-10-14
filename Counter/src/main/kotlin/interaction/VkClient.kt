package interaction

import url.URLReader

open class VkClient(private val publicConfig: PublicConfig, private val privateConfig: PrivateConfig) {
    fun getNewsCount(hashtag: String, startSec: Long, endSec: Long) : VkResponse {
        val query = VkQueryBuilder(publicConfig, privateConfig).countHashtagQuery(hashtag, startSec, endSec)
        return responseToTagsCount(URLReader().urlToResponse(query))
    }
}
