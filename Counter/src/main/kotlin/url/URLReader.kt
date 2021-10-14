package url

import java.io.IOException
import java.io.UncheckedIOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class URLReader {
    fun urlToResponse(url: String): String {
        try {
            with(URL(url).openConnection() as HttpURLConnection) {
                return inputStream.bufferedReader().use { it.readText() }
            }
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }
}