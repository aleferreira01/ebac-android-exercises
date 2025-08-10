package br.com.alexander.awesomemovieapp.movieHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alexander.awesomemovieapp.MovieViewModel
import br.com.alexander.awesomemovieapp.R
import br.com.alexander.awesomemovieapp.databinding.FragmentItemListBinding

class MovieFragment : Fragment(), MovieItemListener {

    private lateinit var adapter: MyMovieRecyclerViewAdapter
    private val viewModel by hiltNavGraphViewModels<MovieViewModel>(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentItemListBinding.inflate(inflater)
        val view = binding.root
        val recyclerView = binding.list

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        adapter = MyMovieRecyclerViewAdapter(this)

        recyclerView.apply {
            this.adapter = this@MovieFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }

        initObservers()

        return view
    }

    private fun initObservers() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateData(it)
            }
        })

        viewModel.navigationToMovieDetails.observe(viewLifecycleOwner, Observer { event ->
           event.getContentIfNotHandled()?.let {
               val action = MovieFragmentDirections.actionMovieFragmentToMovieDetails()
               findNavController().navigate(action)
           }
        })

    }

    @Deprecated("Deprecated in Java", ReplaceWith("inflater.inflate(R.menu.top_menu, menu)"))
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onItemSelected(movieId: Int) {
        viewModel.onMovieSelected(movieId)
    }
}