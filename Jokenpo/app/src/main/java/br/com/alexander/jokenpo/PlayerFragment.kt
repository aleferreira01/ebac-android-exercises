package br.com.alexander.jokenpo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import br.com.alexander.jokenpo.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {

    private lateinit var root: View
    private lateinit var spinner: Spinner
    private lateinit var onItemSelectedListener: OnItemSelectedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onItemSelectedListener = context as OnItemSelectedListener
    }

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
        setupSelectPlaySpinner()

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

    private fun setupSelectPlaySpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.moves_array,
            android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = onItemSelectedListener
    }

}