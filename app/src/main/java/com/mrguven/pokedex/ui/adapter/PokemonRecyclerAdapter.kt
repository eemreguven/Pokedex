package com.mrguven.pokedex.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrguven.pokedex.data.model.PokemonBase
import com.mrguven.pokedex.databinding.ItemPokemonBaseBinding
import com.mrguven.pokedex.ui.fragment.PokemonListFragmentDirections

class PokemonRecyclerAdapter :
    RecyclerView.Adapter<PokemonRecyclerAdapter.PokemonViewHolder>() {

    private val list: ArrayList<PokemonBase> = arrayListOf()

    inner class PokemonViewHolder(private val binding: ItemPokemonBaseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonBase) {
            val imageUrl = "https://img.pokemondb.net/artwork/${item.name}.jpg"
            Glide.with(binding.imageView).load(imageUrl).into(binding.imageView)
            binding.nameTextView.text = item.name ?: ""

            itemView.setOnClickListener {
                val navController = Navigation.findNavController(itemView)
                val action =
                    PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment(
                        item.name
                    )
                navController.navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            ItemPokemonBaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<PokemonBase>?) {
        list.clear()
        newList?.let {
            list.addAll(it)
        }
        notifyDataSetChanged()
    }
}