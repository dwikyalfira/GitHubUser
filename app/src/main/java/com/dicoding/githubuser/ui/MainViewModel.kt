package com.dicoding.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.response.FollowResponseItem
import com.dicoding.githubuser.data.response.GithubResponse
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {

    private val _searchList = MutableLiveData<List<ItemsItem>>()
    val getSearchList: LiveData<List<ItemsItem>> = _searchList

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val getUserDetail: LiveData<DetailUserResponse> = _userDetail

    private val _followers = MutableLiveData<ArrayList<FollowResponseItem>>()
    val getFollowers: LiveData<ArrayList<FollowResponseItem>> = _followers


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object {
        private const val TAG = "MainViewModel"
    }

    fun searchUser(username: String) {
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getSearchUser(username)
            client.enqueue(object : Callback<GithubResponse> {
                override fun onResponse(
                    call: Call<GithubResponse>,
                    response: Response<GithubResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _searchList.value = response.body()?.items
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }
}