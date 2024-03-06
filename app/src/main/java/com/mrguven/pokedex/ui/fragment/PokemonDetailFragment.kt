package com.mrguven.pokedex.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mrguven.pokedex.databinding.FragmentPokemonDetailBinding
import com.mrguven.pokedex.ui.adapter.TypeRecyclerAdapter
import com.mrguven.pokedex.ui.viewmodel.PokemonDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {


    private val viewModel by viewModels<PokemonDetailViewModel>()
    private lateinit var binding: FragmentPokemonDetailBinding
    private lateinit var adapter: TypeRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonName = arguments?.getString("pokemonName")
        viewModel.fetchPokemonDetail(pokemonName!!)
        listenViewModel()
    }

    private fun listenViewModel() {
        viewModel.apply {
            pokemonDetail.observe(viewLifecycleOwner) {
                val imageUrl = "https://img.pokemondb.net/artwork/${it.name}.jpg"
                Glide.with(binding.imageView).load(imageUrl).into(binding.imageView)

                binding.nameText.text = it.name
                val heightText = "${it.height!!.toFloat() / 10.0}M"
                binding.heightText.text = heightText
                val weightText = "${it.weight!!.toFloat() / 10.0}KG"
                binding.weightText.text = weightText

                val types = it?.types?.map { type -> type.type?.name ?: "" } ?: emptyList()
                val recyclerAdapter = TypeRecyclerAdapter(types)
                binding.typesRecyclerView.adapter = recyclerAdapter
                binding.typesRecyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            loadingState.observe(viewLifecycleOwner) {
                binding.loadingStateIndicator.visibility = if (it) View.VISIBLE else View.GONE
                binding.detailInfoCardView.visibility = if (it) View.GONE else View.VISIBLE
            }
        }
    }


}