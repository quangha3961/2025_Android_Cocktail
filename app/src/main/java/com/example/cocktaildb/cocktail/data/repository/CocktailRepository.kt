package com.example.cocktaildb.cocktail.data.repository

import com.example.cocktaildb.cocktail.data.datasource.CocktailDataSource
import com.example.cocktaildb.cocktail.data.model.Cocktail

interface ICocktailRepository {
    fun getCocktails(callback: (List<Cocktail>) -> Unit)
    fun getCocktailById(id: String, callback: (Cocktail?) -> Unit)
}

class CocktailRepository(
    private val dataSource: CocktailDataSource
) : ICocktailRepository {

    override fun getCocktails(callback: (List<Cocktail>) -> Unit) {
        dataSource.getCocktails(callback)
    }

    override fun getCocktailById(id: String, callback: (Cocktail?) -> Unit) {
        dataSource.getCocktailById(id, callback)
    }
}
