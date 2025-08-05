package com.example.cocktaildb

import android.content.Intent
import com.example.cocktaildb.base.BaseActivity
import com.example.cocktaildb.databinding.ActivityMainBinding
import com.example.cocktaildb.home.presentation.view.HomeActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        // Setup any views if needed
    }

    override fun setupListeners() {
        binding.btnNavigateToCocktails.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
