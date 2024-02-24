package com.mrguven.pokedex.data.repository

import com.mrguven.pokedex.data.model.PokemonDetail
import com.mrguven.pokedex.data.model.PokemonListResponse
import com.mrguven.pokedex.data.remote.api.PokemonApiService
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonApiService: PokemonApiService
) {
    suspend fun getPokemonList(offset: Int): PokemonListResponse {
        return pokemonApiService.getPokemonList(offset, 20)
    }

    suspend fun getPokemonDetail(name: String): PokemonDetail {
        return pokemonApiService.getPokemonDetail(name)
    }
}
