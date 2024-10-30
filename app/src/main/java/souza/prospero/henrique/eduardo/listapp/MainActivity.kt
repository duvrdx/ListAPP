package souza.prospero.henrique.eduardo.listapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import souza.prospero.henrique.eduardo.listapp.ui.theme.ListAPPTheme

class MainActivity : ComponentActivity() {
    private val contentList: List<Content> = listOf(
        Content(
            imageId = R.drawable.ic_launcher_foreground,
            title = "Titulo", description = "Descrição"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListAPPTheme {

                ListItemsView(
                    contentList
                )
            }
        }
    }
}

data class Content(val imageId: Int, val title: String, val description: String)


@Composable
fun ListItemsView(contentList: List<Content>) {
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            val context = LocalContext.current
            FloatingActionButton(
                onClick = {
                    context.startActivity(Intent(context, NewItemActivity::class.java))
                },
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {

            items(contentList) { content ->
                ImageHolder(modifier = Modifier.heightIn(max = 100.dp), content = content)

            }
        }
    }
}

@Composable
fun ImageHolder(modifier: Modifier = Modifier, content: Content) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(modifier = modifier.weight(1F)) {
            Image(
                painter = painterResource(id = content.imageId),
                contentDescription = null,
                modifier = modifier.fillMaxSize()
            )
        }
        Column(
            modifier = modifier
                .weight(4F)
                .padding(8.dp)
        ) {
            Text(
                text = content.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = modifier
                    .weight(1F)
                    .fillMaxWidth()
            )
            Spacer(modifier.height(8.dp))
            Text(
                text = content.description, modifier = modifier
                    .weight(2F)
                    .fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListItemsViewPreview() {
    val mock: List<Content> = listOf(
        Content(
            imageId = R.drawable.ic_launcher_foreground,
            title = "Titulo 1", description = "Descrição"
        ),
        Content(
            imageId = R.drawable.ic_launcher_foreground,
            title = "Titulo 2", description = "Descrição"
        ),
        Content(
            imageId = R.drawable.ic_launcher_foreground,
            title = "Titulo 3", description = "Descrição"
        ),
        Content(
            imageId = R.drawable.ic_launcher_foreground,
            title = "Titulo 4", description = "Descrição"
        )
    )

    ListItemsView(mock)
}