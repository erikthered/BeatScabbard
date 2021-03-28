import androidx.compose.desktop.Window
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import java.awt.Desktop
import java.io.File

fun main() = Window(
    title = "BeatScabbard",
    size = IntSize(1024, 768)
) {
    // TODO make this configurable/detectable
    val loader = CustomLevelsLoader("D:\\Games\\SteamLibrary\\steamapps\\common\\Beat Saber\\Beat Saber_Data\\CustomLevels")

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp)
        ) {
            val verticalState = rememberScrollState(0)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalState)
                    .padding(end = 12.dp, bottom = 12.dp)
            ) {
                Column {
                    loader.getSongData().map { song ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                shape = RoundedCornerShape(10),
                                color = Color(0, 0, 0, 20)
                            )
                            .padding(15.dp)
                        ) {
                            Column {
                                Image(
                                    bitmap = org.jetbrains.skija.Image.makeFromEncoded(File(song.path + File.separator + song.coverImage).readBytes())
                                        .asImageBitmap(),
                                    contentDescription = "Cover Image",
                                    Modifier.height(100.dp)
                                )
                            }
                            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                                Row {
                                    Text(song.name)
                                }
                                Row(modifier = Modifier.padding(vertical = 10.dp)) {
                                    Button(onClick = {
                                        // TODO is there a way to do this with Compose instead of AWT?
                                        val desktop = Desktop.getDesktop()
                                        val file = File(song.path!!)
                                        desktop.open(file)
                                    }) {
                                        Text("Open in File Manager")
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(verticalState)
            )
        }
    }
}