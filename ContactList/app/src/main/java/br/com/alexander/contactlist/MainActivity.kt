package br.com.alexander.contactlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alexander.contactlist.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ContactListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerContact.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ContactAdapter(addContactData(), this@MainActivity)
        }
    }

    private fun addContactData(): MutableList<ContactInfo> {
        val list = mutableListOf<ContactInfo>()

        list.add(ContactInfo("João", "(68) 91111-1111"))
        list.add(ContactInfo("Maria", "(82) 92222-2222"))
        list.add(ContactInfo("Carlos", "(96) 93333-3333"))
        list.add(ContactInfo("Ana", "(92) 94444-4444"))
        list.add(ContactInfo("Rodrigo", "(71) 95555-5555"))
        list.add(ContactInfo("Juliana", "(85) 96666-6666"))
        list.add(ContactInfo("Rafael", "(61) 97777-7777"))
        list.add(ContactInfo("Laura", "(27) 98888-8888"))
        list.add(ContactInfo("Thiago", "(62) 99999-9999"))
        list.add(ContactInfo("Isabela", "(98) 91111-1111"))
        list.add(ContactInfo("Lucas", "(31) 92222-2222"))
        list.add(ContactInfo("Fernanda", "(81) 93333-3333"))
        list.add(ContactInfo("Victor", "(21) 94444-4444"))
        list.add(ContactInfo("Camila", "(48) 95555-5555"))
        list.add(ContactInfo("Gabriel", "(11) 96666-6666"))

        return list
    }

    override fun onItemClicked(content: String) {
        Snackbar.make(binding.root, content, Snackbar.LENGTH_SHORT).show()
    }

}