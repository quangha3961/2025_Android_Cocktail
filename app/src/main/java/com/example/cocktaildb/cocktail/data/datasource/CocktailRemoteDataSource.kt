package com.example.cocktaildb.cocktail.data.datasource

import com.example.cocktaildb.cocktail.data.model.CocktailApiResponse
import com.example.cocktaildb.cocktail.data.model.Drink
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import org.json.JSONObject

class CocktailRemoteDataSource : CocktailDataSource {

    companion object {
        private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
        private const val SEARCH_ENDPOINT = "search.php?f=a"
        private const val CONNECTION_TIMEOUT = 10000
        private const val READ_TIMEOUT = 10000
        private const val COCKTAILS_TO_SKIP = 7
        private const val COCKTAILS_TO_TAKE = 4
    }

    private val executor = Executors.newSingleThreadExecutor()

    override fun getCocktails(callback: (List<com.example.cocktaildb.cocktail.data.model.Cocktail>) -> Unit) {
        executor.execute {
            try {
                val url = URL("$BASE_URL$SEARCH_ENDPOINT")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = CONNECTION_TIMEOUT
                connection.readTimeout = READ_TIMEOUT

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()

                    val jsonObject = JSONObject(response.toString())
                    val apiResponse = parseCocktailApiResponse(jsonObject)

                    val cocktails = apiResponse.drinks?.drop(COCKTAILS_TO_SKIP)?.take(COCKTAILS_TO_TAKE)?.map { it.toCocktail() } ?: emptyList()
                    callback(cocktails)
                } else {
                    callback(emptyList())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                callback(emptyList())
            }
        }
    }

    override fun getCocktailById(id: String, callback: (com.example.cocktaildb.cocktail.data.model.Cocktail?) -> Unit) {
        executor.execute {
            try {
                val url = URL("${BASE_URL}lookup.php?i=$id")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = CONNECTION_TIMEOUT
                connection.readTimeout = READ_TIMEOUT

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()

                    val jsonObject = JSONObject(response.toString())
                    val apiResponse = parseCocktailApiResponse(jsonObject)

                    val cocktail = apiResponse.drinks?.firstOrNull()?.toCocktail()
                    callback(cocktail)
                } else {
                    callback(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                callback(null)
            }
        }
    }

    private fun parseCocktailApiResponse(jsonObject: JSONObject): CocktailApiResponse {
        val drinks = mutableListOf<Drink>()

        if (jsonObject.has("drinks") && !jsonObject.isNull("drinks")) {
            val drinksArray = jsonObject.getJSONArray("drinks")
            for (i in 0 until drinksArray.length()) {
                val drinkObject = drinksArray.getJSONObject(i)
                val drink = Drink(
                    idDrink = drinkObject.optString("idDrink"),
                    strDrink = drinkObject.optString("strDrink"),
                    strCategory = drinkObject.optString("strCategory"),
                    strAlcoholic = drinkObject.optString("strAlcoholic"),
                    strGlass = drinkObject.optString("strGlass"),
                    strInstructions = drinkObject.optString("strInstructions"),
                    strDrinkThumb = drinkObject.optString("strDrinkThumb"),
                    strIngredient1 = drinkObject.optString("strIngredient1"),
                    strIngredient2 = drinkObject.optString("strIngredient2"),
                    strIngredient3 = drinkObject.optString("strIngredient3"),
                    strIngredient4 = drinkObject.optString("strIngredient4"),
                    strIngredient5 = drinkObject.optString("strIngredient5"),
                    strMeasure1 = drinkObject.optString("strMeasure1"),
                    strMeasure2 = drinkObject.optString("strMeasure2"),
                    strMeasure3 = drinkObject.optString("strMeasure3"),
                    strMeasure4 = drinkObject.optString("strMeasure4"),
                    strMeasure5 = drinkObject.optString("strMeasure5")
                )
                drinks.add(drink)
            }
        }

        return CocktailApiResponse(drinks)
    }
}
