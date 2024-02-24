package com.mrguven.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class PokemonDetail(
    @SerializedName("name") val name: String?,
    @SerializedName("height") val height: Int?,
    @SerializedName("weight") val weight: Int?,
)
