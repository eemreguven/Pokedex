package com.mrguven.pokedex.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrguven.pokedex.data.model.PokemonDetail
import com.mrguven.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private val _pokemonDetail = MutableLiveData<PokemonDetail>()
    val pokemonDetail : LiveData<PokemonDetail>  get() = _pokemonDetail


    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    fun fetchPokemonDetail(name : String) {
        _loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = pokemonRepository.getPokemonDetail(name)
                _pokemonDetail.postValue(response)
            } catch (e: Exception) {
                Log.e("FetchPokemonDetail", "Error fetching Pokemon detail", e)
            } finally {
                _loadingState.postValue(false)
            }
        }
    }
}