package br.com.alexander.jokenpo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import br.com.alexander.jokenpo.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {

    private lateinit var root: View
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPlayerBinding.inflate(inflater, container, false)
        root = binding.root

        spinner = binding.movesSpinner

        savedInstanceState?.getString("move_spinner")?.let {
            spinner.setSelection(it.toInt())
        }

        setHasOptionsMenu(true)

        lifecycle.addObserver(CustomObserver())

        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("move_spinner", spinner.selectedItemPosition.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())
    }

}