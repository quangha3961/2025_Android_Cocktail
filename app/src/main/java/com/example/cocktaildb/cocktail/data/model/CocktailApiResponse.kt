package com.example.cocktaildb.cocktail.data.model

data class CocktailApiResponse(
    val drinks: List<Drink>?
)

data class Drink(
    val idDrink: String,
    val strDrink: String,
    val strCategory: String?,
    val strAlcoholic: String?,
    val strGlass: String?,
    val strInstructions: String?,
    val strDrinkThumb: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?
) {
    fun toCocktail(): Cocktail {
        val ingredientsWithMeasures = buildIngredientsWithMeasures()

        return Cocktail(
            id = idDrink,
            name = strDrink,
            description = strCategory ?: "Cocktail",
            imageUrl = strDrinkThumb ?: "",
            ingredients = ingredientsWithMeasures,
            instructions = strInstructions ?: ""
        )
    }

    private fun buildIngredientsWithMeasures(): List<String> {
        val ingredients = mutableListOf<String>()
        val measures = mutableListOf<String>()

        addIngredientIfValid(ingredients, measures, strIngredient1, strMeasure1)
        addIngredientIfValid(ingredients, measures, strIngredient2, strMeasure2)
        addIngredientIfValid(ingredients, measures, strIngredient3, strMeasure3)
        addIngredientIfValid(ingredients, measures, strIngredient4, strMeasure4)
        addIngredientIfValid(ingredients, measures, strIngredient5, strMeasure5)

        return ingredients.zip(measures) { ingredient, measure ->
            if (measure.isNotBlank()) "$ingredient ($measure)" else ingredient
        }
    }

    private fun addIngredientIfValid(
        ingredients: MutableList<String>,
        measures: MutableList<String>,
        ingredient: String?,
        measure: String?
    ) {
        if (!ingredient.isNullOrBlank()) {
            ingredients.add(ingredient)
            measures.add(measure ?: "")
        }
    }
}
