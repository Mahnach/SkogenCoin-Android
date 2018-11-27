package com.skogen.coin.screens.main

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import android.view.View
import com.skogen.coin.screens.main.presentation.view.MainView
import com.skogen.coin.screens.main.presentation.presenter.MainPresenter
import com.skogen.coin.R
import com.skogen.coin.screens.main.home_screen.fragment.HomeFragment
import com.skogen.coin.screens.main.meal_info_screen.fragment.MealInfoFragment
import com.skogen.coin.screens.main.menu_screen.fragment.MenuFragment
import com.skogen.coin.screens.main.profile_screen.fragment.ProfileFragment
import com.skogen.coin.skeleton.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainView, BottomNavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener, View.OnClickListener {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun createPresenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun initViews() {
        setSupportActionBar(mainToolbar)
        supportFragmentManager.addOnBackStackChangedListener(this)
        mainToolbarIv.setOnClickListener(this)
        mainToolbarIvBack.setOnClickListener(this)
        mainBnv.itemIconTintList = null
        mainBnv.setOnNavigationItemSelectedListener(this)
        replaceFragment(R.id.mainContainer, HomeFragment.newInstance(), null)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> {
                supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            R.id.action_search -> {
                supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                addFragment(R.id.mainContainer, MenuFragment.newInstance(), null)
            }
            R.id.action_profile -> {
                supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                addFragment(R.id.mainContainer, ProfileFragment.newInstance(), null)
            }
            R.id.action_settings -> {
//                supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            R.id.action_scan_qr -> {
//                supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
        return true
    }

    override fun onClick(v: View?) {
        v?.let {
            when(v) {
                mainToolbarIv -> {}
                mainToolbarIvBack -> onBackPressed()
            }
        }
    }


    override fun onBackStackChanged() {
        if (supportFragmentManager.findFragmentById(R.id.mainContainer)!!::class.java == MealInfoFragment::class.java) {
            mainToolbar.visibility = View.VISIBLE
            mainToolbarIv.visibility = View.INVISIBLE
            mainToolbarIvBack.visibility = View.VISIBLE
        } else if (supportFragmentManager.findFragmentById(R.id.mainContainer)!!::class.java == ProfileFragment::class.java){
            mainToolbar.visibility = View.GONE
        } else {
            mainToolbar.visibility = View.VISIBLE
            mainToolbarIv.visibility = View.VISIBLE
            mainToolbarIvBack.visibility = View.INVISIBLE
        }
    }
}
