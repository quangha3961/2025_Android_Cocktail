package com.example.cocktaildb.home.presentation.presenter

import android.os.Handler
import android.os.Looper
import com.example.cocktaildb.cocktail.data.model.Cocktail
import com.example.cocktaildb.cocktail.data.repository.ICocktailRepository
import com.example.cocktaildb.home.presentation.contract.HomeContract

class HomePresenter(
    private val repository: ICocktailRepository
) : HomeContract.Presenter {

    private var view: HomeContract.View? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun attachView(view: HomeContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadPopularCocktails() {
        view?.showLoading()

        repository.getCocktails { cocktails ->
            mainHandler.post {
                view?.hideLoading()
                view?.showPopularCocktails(cocktails)
            }
        }
    }

    override fun onCocktailClicked(cocktail: Cocktail) {
        view?.showCocktailDetail(cocktail)
    }

    override fun onViewAllClicked() {
        view?.navigateToAllCocktails()
    }
}
