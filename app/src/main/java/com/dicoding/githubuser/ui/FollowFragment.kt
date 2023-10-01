package com.dicoding.githubuser.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.dicoding.githubuser.data.response.FollowResponseItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.databinding.FragmentFollowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabs = arguments?.getInt("tab")
        val username = arguments?.getString("username")
        binding.rvUser.adapter = FollowAdapter()

        try {
            username?.let {
                val client =
                    when (tabs) {
                        0 -> ApiConfig.getApiService().getListFollowers(username)
                        else -> ApiConfig.getApiService().getListFollowing(username)
                    }
                binding.progressBar.isVisible = true
                client.enqueue(object : Callback<ArrayList<FollowResponseItem>> {
                    override fun onResponse(
                        call: Call<ArrayList<FollowResponseItem>>,
                        response: Response<ArrayList<FollowResponseItem>>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                val adapter = FollowAdapter().apply {
                                    submitList(it)
                                }
                                binding.progressBar.isVisible = false
                                binding.rvUser.adapter = adapter
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<ArrayList<FollowResponseItem>>,
                        t: Throwable
                    ) {
                        Log.e(TAG, "onFailure: ${t.message.toString()}")
                    }

                })
            }
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "Follow Fragment"
        const val EXTRA_POSITION = "position"

        const val FRAGMENT_FOLLOWING = 1
        const val FRAGMENT_FOLLOWER = 2
    }
}