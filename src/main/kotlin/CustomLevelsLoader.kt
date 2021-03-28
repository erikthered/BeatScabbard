import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.io.IOException

class CustomLevelsLoader(val directoryPath: String) {

    private val file: File = File(directoryPath)
    private val objectMapper = jacksonObjectMapper()

    init {
        if(!file.isDirectory || !file.exists()) {
            throw IOException("Invalid path: $directoryPath")
        }
    }

    fun getSongFolders(): Array<String> = file.list()!!

    fun getSongData(): List<Song> {
        println("Getting song data")
        return file.walkTopDown()
            .filter { it.name.toLowerCase() == "info.dat" }
            .map { file ->
                val song = objectMapper.readValue<Song>(file)
                song.path = file.parentFile.absolutePath
                song
            }
            .toList()
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Song(
    var path: String?,
    @JsonProperty("_songName") val name: String,
    @JsonProperty("_coverImageFilename") val coverImage: String?
)