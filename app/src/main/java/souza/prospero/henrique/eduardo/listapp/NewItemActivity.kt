package souza.prospero.henrique.eduardo.listapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.constraintlayout.compose.ConstraintLayout
import souza.prospero.henrique.eduardo.listapp.ui.theme.ListAPPTheme

class NewItemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListAPPTheme {
                NewItem(
                    name = "Android",
                )
            }
        }
    }
}

@Composable
fun NewItem(name: String, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("Hello") }

    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Image(
            painter = painterResource(id = R.drawable.baseline_image_24),
            contentDescription = "stringResource(id = R.string.id = R.string.)",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Button(
            onClick = { println("ButtonClicked") },
            contentPadding = PaddingValues(all = 0.dp),
            shape = RectangleShape,
            modifier = Modifier.wrapContentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_image_search_24),
                contentDescription = "stringResource(id = R.string.dog_content_description)",
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp)
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Label") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)

            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Label") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4F)


            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { println() },
                modifier = Modifier
                    .weight(1F)

            ) {
                Text(
                    "Send Image"
                )
            }
        }


    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ListAPPTheme {
        NewItem("Android")
    }
}