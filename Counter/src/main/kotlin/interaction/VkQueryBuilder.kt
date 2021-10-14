package interaction

import java.net.URLEncoder

class VkQueryBuilder(private val publicConfig: PublicConfig, private val privateConfig: PrivateConfig,) {
    private val URL_ENCODING = "utf-8"

    fun countHashtagQuery(hashtag: String, startSec: Long, endSec: Long) : String {
        return """${publicConfig.protocol}://${publicConfig.api}/method/${publicConfig.method}?q=${URLEncoder.encode("#${hashtag}", URL_ENCODING)}&start_time=$startSec&end_time=$endSec&count=0&access_token=${privateConfig.token}&v=${publicConfig.version}"""
    }
}

