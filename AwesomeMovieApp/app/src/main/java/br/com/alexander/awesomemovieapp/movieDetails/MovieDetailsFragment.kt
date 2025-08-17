package br.com.alexander.awesomemovieapp.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import br.com.alexander.awesomemovieapp.MovieViewModel
import br.com.alexander.awesomemovieapp.R
import br.com.alexander.awesomemovieapp.databinding.FragmentMovieDetailsBinding
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class MovieDetailsFragment : Fragment() {

    private val viewModel by hiltNavGraphViewModels<MovieViewModel>(R.id.nav_graph)
    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_details,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val carousel: ImageCarousel = binding.movieCarousel
        carousel.registerLifecycle(viewLifecycleOwner)

        viewModel.moviePostersLiveData.observe(viewLifecycleOwner, Observer { list ->
            val posters = mutableListOf<CarouselItem>()
            list.take(6).forEach {
                posters.add(CarouselItem(imageUrl = it.getImageUrl()))
            }
            carousel.setData(posters)
        })

        return binding.root
    }

}