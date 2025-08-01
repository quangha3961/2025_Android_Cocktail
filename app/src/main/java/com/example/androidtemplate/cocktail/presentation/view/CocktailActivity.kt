package com.example.androidtemplate.cocktail.presentation.view

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtemplate.base.BaseActivity
import com.example.androidtemplate.cocktail.data.datasource.CocktailLocalDataSource
import com.example.androidtemplate.cocktail.data.repository.CocktailRepository
import com.example.androidtemplate.cocktail.data.repository.ICocktailRepository
import com.example.androidtemplate.cocktail.presentation.adapter.CocktailAdapter
import com.example.androidtemplate.cocktail.presentation.contract.CocktailContract
import com.example.androidtemplate.cocktail.presentation.presenter.CocktailPresenter
import com.example.androidtemplate.databinding.ActivityCocktailBinding

class CocktailActivity : BaseActivity<ActivityCocktailBinding>(), CocktailContract.View {

    private lateinit var presenter: CocktailPresenter
    private lateinit var repository: ICocktailRepository
    private lateinit var adapter: CocktailAdapter

    override fun getViewBinding(): ActivityCocktailBinding {
        return ActivityCocktailBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        // Dependency injection setup
        val dataSource = CocktailLocalDataSource()
        repository = CocktailRepository(dataSource)
        presenter = CocktailPresenter(repository)
        presenter.attachView(this)

        adapter = CocktailAdapter { cocktail ->
            presenter.onCocktailClicked(cocktail)
        }

        binding.rvCocktails.apply {
            layoutManager = LinearLayoutManager(this@CocktailActivity)
            adapter = this@CocktailActivity.adapter
        }
    }

    override fun setupListeners() {
        binding.btnLoadCocktails.setOnClickListener {
            presenter.loadCocktails()
        }
    }

    override fun showCocktails(cocktails: List<com.example.androidtemplate.cocktail.data.model.Cocktail>) {
        adapter.submitList(cocktails)
    }

    override fun showCocktailDetail(cocktail: com.example.androidtemplate.cocktail.data.model.Cocktail) {
        Toast.makeText(
            this,
            "Selected: ${cocktail.name}",
            Toast.LENGTH_SHORT
        ).show()
        // Future: Navigate to detail screen
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
