import kotlin.test.Test

class CustomLevelsLoaderTest {

    var loader = CustomLevelsLoader("D:\\Games\\SteamLibrary\\steamapps\\common\\Beat Saber\\Beat Saber_Data\\CustomLevels")

    @Test
    fun testListing() {
        loader.getSongFolders().forEach { println(it) }
    }

    @Test
    fun testParsing() {
        val songs = loader.getSongData()
        assert(songs.isNotEmpty())
    }
}