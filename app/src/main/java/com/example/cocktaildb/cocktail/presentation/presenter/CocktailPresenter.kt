package com.example.cocktaildb.cocktail.presentation.presenter

import android.os.Handler
import android.os.Looper
import com.example.cocktaildb.cocktail.data.model.Cocktail
import com.example.cocktaildb.cocktail.data.repository.ICocktailRepository
import com.example.cocktaildb.cocktail.presentation.contract.CocktailContract

class CocktailPresenter(
    private val repository: ICocktailRepository
) : CocktailContract.Presenter {

    private var view: CocktailContract.View? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun attachView(view: CocktailContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadCocktails() {
        view?.showLoading()

        repository.getCocktails { cocktails ->
            mainHandler.post {
                view?.hideLoading()
                view?.showCocktails(cocktails)
            }
        }
    }

    override fun onCocktailClicked(cocktail: Cocktail) {
        view?.showCocktailDetail(cocktail)
    }
}
