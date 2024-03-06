package com.mrguven.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("slot") val slot: Int?,
    @SerializedName("type") val type: TypeDetail?
)

data class TypeDetail(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)

data class PokemonDetail(
    @SerializedName("name") val name: String?,
    @SerializedName("height") val height: Int?,
    @SerializedName("weight") val weight: Int?,
    @SerializedName("types") val types : List<Type>?
)
