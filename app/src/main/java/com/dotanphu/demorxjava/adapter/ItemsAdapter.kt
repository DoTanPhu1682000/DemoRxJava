package com.dotanphu.demorxjava.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dotanphu.demorxjava.databinding.ItemNewyorkBinding
import com.dotanphu.demorxjava.model.Item
import java.lang.Math.pow
import java.util.stream.Stream

class ItemsAdapter(private var itemsList: ArrayList<Item>) : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {
//    val itemsList = mutableListOf<Item>()
//    private val itemsList = arrayListOf<Item>()
//    private var itemsList: ArrayList<Item>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder(
            ItemNewyorkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount() = itemsList.size

    inner class ItemsViewHolder(private val binding: ItemNewyorkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            val randomInteger = (1..120).shuffled().first()
            binding.tvNameDetail.text = item.name
            binding.tvHasIssuesDetail.text = item.has_issues.toString()
            binding.tvAgeDetail.text = item.age.toString()
//            binding.tvAgeDetail.text = "$randomInteger"
        }
    }

}