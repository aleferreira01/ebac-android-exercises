package br.com.alexander.contactlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alexander.contactlist.databinding.ContactItemBinding

class ContactAdapter(
    private val contactList: MutableList<ContactInfo>,
    private val contactListener: ContactListener
) : RecyclerView.Adapter<ContactAdapter.ContactItemViewHolder>() {

    class ContactItemViewHolder(binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var root = binding.root
        var contactName = binding.textContactName
        var contactPhone = binding.textContactPhone
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItemViewHolder {
        return ContactItemViewHolder(ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = contactList.size

    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) {
        val item = contactList[position]
        holder.contactName.text = item.name
        holder.contactPhone.text = item.phone

        holder.root.setOnClickListener {
            contactListener.onItemClicked(item.name)
        }
    }
}