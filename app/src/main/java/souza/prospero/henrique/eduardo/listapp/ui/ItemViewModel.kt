import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Item(
    var bitmap: Bitmap? = null,
    var title: String = "",
    var description: String = ""
)

data class ItemsUiState(
    val items: List<Item> = listOf(),
    var currentItem: Item? = null
)

class ItemsViewModel private constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ItemsUiState())
    val uiState: StateFlow<ItemsUiState> = _uiState.asStateFlow()

    // Usando MutableStateFlow para o bitmap
    private val _currentBitmap = MutableStateFlow<Bitmap?>(null)
    val currentBitmap: StateFlow<Bitmap?> = _currentBitmap.asStateFlow()

    // Usando MutableStateFlow para título e descrição
    private val _currentTitle = MutableStateFlow("")
    val currentTitle: StateFlow<String> get() = _currentTitle.asStateFlow()

    private val _currentDescription = MutableStateFlow("")
    val currentDescription: StateFlow<String> get() = _currentDescription.asStateFlow()

    fun addItem(item: Item) {
        _uiState.update { currentState ->
            currentState.copy(
                items = currentState.items + item
            )
        }
    }

    fun setCurrentTitle(title: String) {
        _currentTitle.value = title // Atualiza o StateFlow
    }

    fun setCurrentDescription(description: String) {
        _currentDescription.value = description // Atualiza o StateFlow
    }

    fun setCurrentBitmap(bitmap: Bitmap?) {
        _currentBitmap.value = bitmap // Atualiza o StateFlow
    }

    fun getItems(): List<Item> {
        return _uiState.value.items
    }

    companion object {
        @Volatile
        private var instance: ItemsViewModel? = null

        fun getInstance(): ItemsViewModel {
            return instance ?: synchronized(this) {
                instance ?: ItemsViewModel().also { instance = it }
            }
        }
    }
}
