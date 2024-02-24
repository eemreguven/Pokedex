package com.mrguven.pokedex.di.modules

import com.mrguven.pokedex.data.remote.api.PokemonApiService
import com.mrguven.pokedex.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providePokemonRepository(
        pokemonApiService: PokemonApiService
    ): PokemonRepository {
        return PokemonRepository(pokemonApiService)
    }
}
