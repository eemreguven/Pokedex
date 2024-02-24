package com.mrguven.pokedex.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrguven.pokedex.data.model.PokemonBase
import com.mrguven.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokemonBase>>()
    val pokemonList: LiveData<List<PokemonBase>> = _pokemonList

    private val _itemsOffset = MutableLiveData<Int>()
    val itemsOffset: LiveData<Int> get() = _itemsOffset


    private val _currentItemsText = MutableLiveData<String>()
    val currentItemsText: LiveData<String> get() = _currentItemsText

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    init {
        _itemsOffset.postValue(INITIAL_ITEMS_OFFSET)
        fetchPokemonList(INITIAL_ITEMS_OFFSET)
    }

    private fun fetchPokemonList(offset: Int) {
        _loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = pokemonRepository.getPokemonList(offset)

                val resultList = response.results
                _pokemonList.postValue(resultList)
            } catch (e: Exception) {
                Log.e("FetchPokemonList", "Error fetching Pokemon list", e)
            } finally {
                _loadingState.postValue(false)
                setCurrentItemsText()
            }
        }
    }

    fun fetchPreviousPagePokemonList() {
        viewModelScope.launch {
            if (itemsOffset.value!! > 0) {
                val newOffset = itemsOffset.value!! - PAGE_ITEM_LIMIT
                _itemsOffset.postValue(newOffset)
                fetchPokemonList(newOffset)
            }
        }
    }

    fun fetchNextPagePokemonList() {
        viewModelScope.launch {
            Log.e("OFFFFFS", "old = ${itemsOffset.value}")
                val newOffset = itemsOffset.value!! + PAGE_ITEM_LIMIT
                _itemsOffset.postValue(newOffset)
            Log.e("OFFFFFS", "new = $newOffset")
            Log.e("OFFFFFS", "update = ${itemsOffset.value}")
            fetchPokemonList(newOffset)

        }
    }

    private fun setCurrentItemsText() {
        viewModelScope.launch {

            val startText = itemsOffset.value!! + 1
            val endText =
                itemsOffset.value!! + _pokemonList.value!!.size

            val text = "$startText-$endText"
            _currentItemsText.postValue(text)
        }
    }

    companion object {
        const val PAGE_ITEM_LIMIT = 20
        const val INITIAL_ITEMS_OFFSET = 0
    }
}