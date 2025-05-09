package br.com.alexander.awesomemovieapp.movieHome

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alexander.awesomemovieapp.data.Movie
import br.com.alexander.awesomemovieapp.databinding.FragmentItemBinding


interface MovieItemListener {
    fun onItemSelected(movieId: Int)
}

class MyMovieRecyclerViewAdapter(
    private val listener: MovieItemListener
) : RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder>() {

    private var values: List<Movie> = ArrayList()

    fun updateData(movieList: List<Movie>) {
        values = movieList
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
            listener.onItemSelected(item.id)
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val view: View = binding.root
        val seeMoreButton = binding.seeMore

        fun bindItem(item: Movie) {
            binding.movieItem = item
            binding.executePendingBindings()
        }
    }

}