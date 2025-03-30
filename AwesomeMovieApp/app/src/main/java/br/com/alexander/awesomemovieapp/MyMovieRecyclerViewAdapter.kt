package br.com.alexander.awesomemovieapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import br.com.alexander.awesomemovieapp.placeholder.PlaceholderContent.PlaceholderItem
import br.com.alexander.awesomemovieapp.databinding.FragmentItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
interface MovieItemListener {
    fun onItemSelected(position: Int)
}

class MyMovieRecyclerViewAdapter(
    private val listener: MovieItemListener
) : RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder>() {

    private val values: MutableList<PlaceholderItem> = ArrayList()

    fun updateData(movieList: List<PlaceholderItem>) {
        values.clear()
        values.addAll(movieList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bindItem(item)

        holder.seeMoreButton.setOnClickListener {
            listener.onItemSelected(position)
        }

//        holder.movieName.text = holder.view.context.getString(
//            R.string.temp_title_movie,
//            item.id,
//            holder.view.context.getString(R.string.movie_title_example)
//        )
//        holder.movieSynopsis.text = holder.view.context.getString(R.string.movie_desc_example)
//        holder.movieCover.setImageResource(R.drawable.movie_cover)
//
//        holder.seeMoreButton.setOnClickListener {
//            listener.onItemSelected(position)
//        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val view: View = binding.root
        val seeMoreButton = binding.seeMore

        fun bindItem(item: PlaceholderItem) {
            binding.movieItem = item
            binding.executePendingBindings()
        }
//        val movieName: TextView = binding.movieName
//        val movieSynopsis: TextView = binding.movieSynopsis
//        val seeMoreButton: Button = binding.seeMore
//        val movieCover: ImageView = binding.coverImage
    }

}