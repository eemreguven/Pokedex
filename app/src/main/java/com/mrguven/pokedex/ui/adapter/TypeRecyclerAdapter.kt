package com.mrguven.pokedex.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mrguven.pokedex.R
import com.mrguven.pokedex.databinding.ItemTextBinding

class TypeRecyclerAdapter(private val typeNamesList: List<String>) :
    RecyclerView.Adapter<TypeRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeRecyclerAdapter.ViewHolder {
        val binding =
            ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = typeNamesList[position]
        holder.bind(text)
    }

    override fun getItemCount(): Int {
        return typeNamesList.size
    }

    inner class ViewHolder(private val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(typeName: String) {
            binding.textView.text = typeName
            binding.textView.backgroundTintList = getTintListForType(binding.root.context,typeName)
        }
    }

    fun getTintListForType(context: Context, type: String): ColorStateList {
        val colorResId = when (type.lowercase()) {
            "normal" -> R.color.normal
            "fire" -> R.color.fire
            "water" -> R.color.water
            "electric" -> R.color.electric
            "grass" -> R.color.grass
            "ice" -> R.color.ice
            "fighting" -> R.color.fighting
            "poison" -> R.color.poison
            "ground" -> R.color.ground
            "flying" -> R.color.flying
            "psychic" -> R.color.psychic
            "bug" -> R.color.bug
            "rock" -> R.color.rock
            "ghost" -> R.color.ghost
            "dark" -> R.color.dark
            "dragon" -> R.color.dragon
            "steel" -> R.color.steel
            "fairy" -> R.color.fairy
            else -> R.color.default_color
        }
        return ColorStateList.valueOf(ContextCompat.getColor(context, colorResId))
    }

}