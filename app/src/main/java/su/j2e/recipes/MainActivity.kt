package su.j2e.recipes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import su.j2e.recipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels<MainViewModelImp>()
    private val binding by lazy { ActivityMainBinding.bind(findViewById(R.id.root)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = RecipesRvAdapter {
            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(it.url)))
        }
        binding.recipesRv.adapter = adapter
        binding.retry.setOnClickListener { viewModel.retry() }

        viewModel.recipes.observe(this) {
            adapter.submitList(it)
            binding.emptyListText.isVisible = it.isEmpty()
        }
        viewModel.loading.observe(this) {
            binding.loadingProgress.isVisible = it
        }
        viewModel.retryVisible.observe(this) {
            binding.retry.isVisible = it
        }
        viewModel.error.consume(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotBlank()) {
                    viewModel.loadRecipes(query)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String) = false
        })
        return super.onCreateOptionsMenu(menu)
    }
}
