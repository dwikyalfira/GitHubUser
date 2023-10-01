package com.dicoding.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        adapter = UserAdapter()

        val layoutManager = LinearLayoutManager(this)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.getSearchList.observe(this) { getUserData ->
            setUserData(getUserData)
        }

        with(binding) {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    mainViewModel.searchUser(searchView.text.toString())
                    searchView.hide()
                    false

                }
        }
    }

    private fun setUserData(user: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


//    fun toggleTheme(view: View) {
//        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
//
//        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
//            // Switch to light mode
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            binding.btnTheme.setBackgroundResource(R.drawable.ic_light_mode)
//        } else {
//            // Switch to dark mode
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            binding.btnTheme.setBackgroundResource(R.drawable.ic_dark_mode)
//        }
//    }

}