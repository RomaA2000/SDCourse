import com.beust.klaxon.Klaxon
import counter.HashTagCounter
import interaction.PrivateConfig
import interaction.PublicConfig
import interaction.VkClient
import java.io.FileReader
import java.lang.IllegalArgumentException

fun main(args: Array<String>) {
    if (args.size != 4) {
        throw IllegalArgumentException("Invalid number of arguments")
    }
    try {
        val publicPath = args[0]
        val privatePath = args[1]
        val hashtag = args[2]
        val numberOfSegments = args[3].toInt()


        val publicCfg = Klaxon().parse<PublicConfig>(FileReader(publicPath))
        val privateCfg = Klaxon().parse<PrivateConfig>(FileReader(privatePath))
        if (numberOfSegments > 24) {
            throw IllegalArgumentException("Invalid number of segments")
        } else if (publicCfg == null || privateCfg == null) {
            throw IllegalArgumentException("Failed to get configs")
        }
        val newsFeedChart = HashTagCounter(VkClient(publicCfg, privateCfg))
        val seq = newsFeedChart.count(hashtag, numberOfSegments)
        print(seq)
    } catch (e : Exception) {
        print("Exception ${e.message}")
    }
}