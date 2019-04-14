package com.rodrigoelias.testwise.data.v2

data class pokedex(
        val descriptions: List<Description>,
        val id: Int,
        val is_main_series: Boolean,
        val name: String,
        val names: List<Name>,
        val pokemon_entries: List<PokemonEntry>,
        val region: Region,
        val version_groups: List<VersionGroup>
)

data class PokemonEntry(
        val entry_number: Int,
        val pokemon_species: PokemonSpecies
)

data class PokemonSpecies(
        val name: String,
        val url: String
)

data class VersionGroup(
        val name: String,
        val url: String
)

data class Region(
        val name: String,
        val url: String
)

data class Language(
        val name: String,
        val url: String
)

data class Name(
        val language: Language,
        val name: String
)

data class Description(
        val description: String,
        val language: Language
)