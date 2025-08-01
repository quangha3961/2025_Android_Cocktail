package com.example.androidtemplate

import android.content.Intent
import com.example.androidtemplate.base.BaseActivity
import com.example.androidtemplate.cocktail.presentation.view.CocktailActivity
import com.example.androidtemplate.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        // Setup any views if needed
    }

    override fun setupListeners() {
        binding.btnNavigateToCocktails.setOnClickListener {
            val intent = Intent(this, CocktailActivity::class.java)
            startActivity(intent)
        }
    }
}
