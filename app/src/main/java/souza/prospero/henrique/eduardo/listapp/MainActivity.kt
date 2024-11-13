package souza.prospero.henrique.eduardo.listapp

import Item
import ItemsViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import souza.prospero.henrique.eduardo.listapp.ui.theme.ListAPPTheme

class MainActivity : ComponentActivity() {
    private val itemsViewModel: ItemsViewModel by lazy { ItemsViewModel.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        println(itemsViewModel.getItems())

        setContent {
            ListAPPTheme {
                // Observe o estado da UI
                val uiState by itemsViewModel.uiState.collectAsState() // Colete o estado da ViewModel
                ListItemsView(uiState.items) // Passa a lista de itens
            }
        }
    }
}

@Composable
fun ListItemsView(items: List<Item>) {
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
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
            items(items) { content ->
                ImageHolder(content = content) // Renderiza cada item
            }
        }
    }
}



@Composable
fun ImageHolder(content: Item) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp) // Tamanho fixo para as imagens
                    .aspectRatio(1f) // Mantém a proporção quadrada

            ) {
                Image(
                    painter = if (content.bitmap != null) {
                        BitmapPainter(content.bitmap!!.asImageBitmap())
                    } else {
                        painterResource(id = R.drawable.baseline_image_24)
                    },
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center) // Alinha a imagem ao centro
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(4f)
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            Text(text = content.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = content.description)
        }
    }
}




@Preview(showBackground = true)
@Composable
fun ListItemsViewPreview() {
    ListItemsView(items = listOf(
        Item(title = "Item 1", description = "Descrição do Item 1"),
        Item(title = "Item 2", description = "Descrição do Item 2")
    ))
}