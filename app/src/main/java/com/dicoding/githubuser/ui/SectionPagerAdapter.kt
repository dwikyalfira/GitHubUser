package com.dicoding.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment = FollowFragment()
        val bundle = Bundle()
        bundle.putString("username", username)
        fragment.arguments = when (position) {
            0 -> {
                bundle.putInt("tab", 0)
                bundle
            }

            else -> {
                bundle.putInt("tab", 1)
                bundle
            }
        }
        return fragment
    }
}