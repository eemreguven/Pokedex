package com.mrguven.pokedex.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrguven.pokedex.R
import com.mrguven.pokedex.databinding.FragmentPokemonListBinding
import com.mrguven.pokedex.ui.adapter.PokemonRecyclerAdapter
import com.mrguven.pokedex.ui.viewmodel.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private val viewModel by viewModels<PokemonListViewModel>()
    private val pokemonRecyclerAdapter = PokemonRecyclerAdapter()
    private lateinit var binding: FragmentPokemonListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        listenViewModel()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = pokemonRecyclerAdapter

        binding.nextButton.setOnClickListener {
            viewModel.fetchNextPagePokemonList()
        }
        binding.prevButton.apply {
            setOnClickListener {
                viewModel.fetchPreviousPagePokemonList()
            }
        }
    }

    private fun listenViewModel() {
        viewModel.apply {
            pokemonList.observe(viewLifecycleOwner) {
                pokemonRecyclerAdapter.updateList(it)
                val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
                layoutManager.scrollToPositionWithOffset(0, 0)
            }
            loadingState.observe(viewLifecycleOwner) {
                binding.loadingStateIndicator.visibility = if (it) View.VISIBLE else View.GONE
                binding.recyclerView.visibility = if (it) View.GONE else View.VISIBLE
            }
            itemsOffset.observe(viewLifecycleOwner) {
                binding.prevButton.apply {
                    isEnabled = it > 0
                    backgroundTintList = if (isEnabled) {
                        ColorStateList.valueOf(resources.getColor(R.color.white))
                    } else {
                        ColorStateList.valueOf(resources.getColor(R.color.light_grey))
                    }
                }
            }
            currentItemsText.observe(viewLifecycleOwner) {
                binding.currentItemsTextView.text = it
            }
        }
    }
}