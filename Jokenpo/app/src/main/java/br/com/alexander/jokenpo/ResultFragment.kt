package br.com.alexander.jokenpo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import br.com.alexander.jokenpo.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var root: View
    private lateinit var engine: JokenpoEngine
    private lateinit var resultText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentResultBinding.inflate(inflater, container, false)
        root = binding.root

        engine = JokenpoEngine(resources.getStringArray(R.array.moves_array))

        val currentPlay = arguments?.getString("currentPlay")
        resultText = binding.playerWinnerText

        currentPlay?.let {
            updateResultText(currentPlay)
        }

        setHasOptionsMenu(true)

        lifecycle.addObserver(CustomObserver())

        return root
    }

    private fun updateResultText(currentPlay: String) {
        val resultGame = engine.calculateResult(currentPlay)

        resultText.text = when(resultGame) {
            Result.WIN -> getString(R.string.you_won)
            Result.LOSS -> getString(R.string.you_lost)
            else -> getString(R.string.you_tied)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())
    }
}