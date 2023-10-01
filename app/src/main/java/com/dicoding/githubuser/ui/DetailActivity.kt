package com.dicoding.githubuser.ui

import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailActivity"

        @StringRes
        val TAB_TITLE = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra("key_login")

        username?.let {
            val sectionPagerAdapter = SectionPagerAdapter(this, username)
            val viewPager: ViewPager2 = findViewById(R.id.vp_detail)
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLE[position])
            }.attach()
            supportActionBar?.elevation = 0f

            binding.progressBar.isVisible = true
            ApiConfig
                .getApiService()
                .getDetailUser(it)
                .enqueue(object : Callback<DetailUserResponse> {
                    override fun onResponse(
                        call: Call<DetailUserResponse>,
                        response: Response<DetailUserResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            response.body()?.let {
                                binding.tvName.text = it.name
                                Glide.with(this@DetailActivity)
                                    .load(it.avatarUrl)
                                    .into(binding.ivUser)
                                binding.follower.text = "${it.followers} Followers"
                                binding.following.text = "${it.following} Following"
                                binding.tvUsername.text = "${it.login}"
                            }
                            binding.progressBar.isVisible = false
                        }
                    }

                    override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                        _isLoading.value = false
                        Log.e(TAG, "onFailure: ${t.message.toString()}")
                    }
                })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}