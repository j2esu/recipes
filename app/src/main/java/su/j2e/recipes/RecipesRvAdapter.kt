package su.j2e.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import su.j2e.recipes.databinding.RecipeItemBinding

class RecipesRvAdapter(private val onItemClick: (Recipe) -> Unit) :
    ListAdapter<Recipe, RecipesRvAdapter.RecipeVh>(object : ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.url == newItem.url
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeVh(parent).apply {
        itemView.setOnClickListener { onItemClick(getItem(adapterPosition)) }
    }

    override fun onBindViewHolder(holder: RecipeVh, position: Int) = holder.bind(getItem(position))

    class RecipeVh(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
    ) {

        private val binding = RecipeItemBinding.bind(itemView)

        fun bind(recipe: Recipe) = with(binding) {
            println(recipe)
            title.text = recipe.title
            ingredients.text = recipe.ingredients.joinToString("\n") { "- $it" }
            if (recipe.imgUrl.isNotBlank()) {
                Picasso.get()
                    .load(recipe.imgUrl)
                    .placeholder(R.drawable.ic_image)
                    .into(img)
            } else {
                img.setImageResource(R.drawable.ic_image)
            }
        }
    }
}
