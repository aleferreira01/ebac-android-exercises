package br.com.alexander.awesomemovieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import br.com.alexander.awesomemovieapp.databinding.FragmentItemBinding
import br.com.alexander.awesomemovieapp.databinding.FragmentItemListBinding
import br.com.alexander.awesomemovieapp.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class MovieFragment : Fragment(), MovieItemListener {

    private lateinit var adapter: MyMovieRecyclerViewAdapter
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.nav_graph){ defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentItemListBinding.inflate(inflater)
        val view = binding.root as RecyclerView

        setHasOptionsMenu(true)

        adapter = MyMovieRecyclerViewAdapter(this)

        view.apply {
            this.adapter = this@MovieFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }

        initObservers()

        return view
    }

    private fun initObservers() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
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

    override fun onItemSelected(position: Int) {
        viewModel.onMovieSelected(position, this.requireContext())
        //findNavController().navigate(R.id.movieDetailsFragment)
    }
}