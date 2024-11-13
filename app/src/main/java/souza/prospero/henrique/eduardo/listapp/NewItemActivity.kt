package souza.prospero.henrique.eduardo.listapp

import Item
import ItemsViewModel
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import souza.prospero.henrique.eduardo.listapp.ui.theme.ListAPPTheme
import androidx.compose.ui.platform.LocalConfiguration

class NewItemActivity : ComponentActivity() {
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private val itemsViewModel: ItemsViewModel by lazy { ItemsViewModel.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    itemsViewModel.setCurrentBitmap(bitmap) // Atualiza o bitmap na ViewModel
                }
            }
        }

        setContent {
            ListAPPTheme {
                NewItem(
                    onImagePickerClick = { openImagePicker() },
                    viewModel = itemsViewModel
                )
            }
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }
}

@Composable
fun NewItem(
    onImagePickerClick: () -> Unit,
    viewModel: ItemsViewModel,
    modifier: Modifier = Modifier
) {
    val currentBitmap by viewModel.currentBitmap.collectAsStateWithLifecycle()
    val currentTitle by viewModel.currentTitle.collectAsStateWithLifecycle()
    val currentDescription by viewModel.currentDescription.collectAsStateWithLifecycle()

    // Detecta a configuração atual da tela (paisagem ou retrato)
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == 1 // 1 representa Portrait, 2 representa Landscape

    if (isPortrait) {
        NewItemPortrait(
            currentBitmap,
            onImagePickerClick,
            currentTitle,
            currentDescription,
            viewModel
        )
    } else {
        NewItemLandscape(
            currentBitmap,
            onImagePickerClick,
            currentTitle,
            currentDescription,
            viewModel
        )
    }
}

@Composable
fun NewItemPortrait(
    currentBitmap: Bitmap?,
    onImagePickerClick: () -> Unit,
    currentTitle: String,
    currentDescription: String,
    viewModel: ItemsViewModel
) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = if (currentBitmap != null) {
                BitmapPainter(currentBitmap.asImageBitmap())
            } else {
                painterResource(id = R.drawable.baseline_image_24)
            },
            contentDescription = "Imagem Selecionada",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Button(
            onClick = onImagePickerClick,
            modifier = Modifier.wrapContentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_image_search_24),
                contentDescription = "Selecionar Imagem",
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
                value = currentTitle,
                onValueChange = {
                    viewModel.setCurrentTitle(it) // Atualiza o título na ViewModel
                },
                label = { Text("Título") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = currentDescription,
                onValueChange = {
                    viewModel.setCurrentDescription(it) // Atualiza a descrição na ViewModel
                },
                label = { Text("Descrição") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4F)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.addItem(Item(bitmap = currentBitmap, title = currentTitle, description = currentDescription))
                    println(viewModel.getItems())

                    // Limpa os campos após enviar
                    viewModel.setCurrentTitle("")
                    viewModel.setCurrentDescription("")
                    viewModel.setCurrentBitmap(null)
                },
                modifier = Modifier.weight(1F)
            ) {
                Text("Enviar Imagem")
            }
        }
    }
}

@Composable
fun NewItemLandscape(
    currentBitmap: Bitmap?,
    onImagePickerClick: () -> Unit,
    currentTitle: String,
    currentDescription: String,
    viewModel: ItemsViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
    ){
        Row(
            modifier = Modifier
                .weight(10F),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2F)
            ) {
                Image(
                    painter = if (currentBitmap != null) {
                        BitmapPainter(currentBitmap.asImageBitmap())
                    } else {
                        painterResource(id = R.drawable.baseline_image_24)
                    },
                    contentDescription = "Imagem Selecionada",
                    modifier = Modifier
                        .widthIn(max = 300.dp)
                        .aspectRatio(1f)
                )

                Button(
                    onClick = onImagePickerClick,
                    modifier = Modifier.wrapContentSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_image_search_24),
                        contentDescription = "Selecionar Imagem",
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 250.dp)
                    .padding(16.dp)
                    .weight(1F)
            ) {
                TextField(
                    value = currentTitle,
                    onValueChange = {
                        viewModel.setCurrentTitle(it) // Atualiza o título na ViewModel
                    },
                    label = { Text("Título") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)

                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentDescription,
                    onValueChange = {
                        viewModel.setCurrentDescription(it) // Atualiza a descrição na ViewModel
                    },
                    label = { Text("Descrição") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2F)
                )

            }
        }
        Row(
            modifier = Modifier
                .weight(2F),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(
                onClick = {
                    viewModel.addItem(Item(bitmap = currentBitmap, title = currentTitle, description = currentDescription))
                    println(viewModel.getItems())

                    // Limpa os campos após enviar
                    viewModel.setCurrentTitle("")
                    viewModel.setCurrentDescription("")
                    viewModel.setCurrentBitmap(null)
                },
            ) {
                Text("Enviar Imagem")
            }
        }
    }

}


@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun NewItemLandscapePreview() {
    val itemsViewModel: ItemsViewModel = ItemsViewModel.getInstance()

    ListAPPTheme {
        NewItemLandscape(
            currentBitmap = null, // Simulando sem imagem
            onImagePickerClick = {}, // Não precisa fazer nada no preview
            currentTitle = "Título Exemplo",
            currentDescription = "Descrição Exemplo",
            viewModel = itemsViewModel // Usar a instância da ViewModel
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewItemPortraitPreview() {
    val itemsViewModel: ItemsViewModel = ItemsViewModel.getInstance()

    ListAPPTheme {
        NewItemPortrait(
            currentBitmap = null, // Simulando sem imagem
            onImagePickerClick = {}, // Não precisa fazer nada no preview
            currentTitle = "Título Exemplo",
            currentDescription = "Descrição Exemplo",
            viewModel = itemsViewModel // Usar a instância da ViewModel
        )
    }
}