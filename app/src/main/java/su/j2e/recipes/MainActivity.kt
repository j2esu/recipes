package su.j2e.recipes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels<MainViewModelImp>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.recipes.observe(this) {
            println(it)
        }
        viewModel.loading.observe(this) {
            println(it)
        }
        viewModel.error.consume(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}
