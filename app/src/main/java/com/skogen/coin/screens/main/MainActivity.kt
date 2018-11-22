package com.skogen.coin.screens.main

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import com.skogen.coin.screens.main.presentation.view.MainView
import com.skogen.coin.screens.main.presentation.presenter.MainPresenter
import com.skogen.coin.R
import com.skogen.coin.screens.main.menu_screen.fragment.MenuFragment
import com.skogen.coin.skeleton.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainView, BottomNavigationView.OnNavigationItemSelectedListener {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun createPresenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun initViews() {
        mainBnv.itemIconTintList = null
        mainBnv.setOnNavigationItemSelectedListener(this)
//        replaceFragment(R.id.mainContainer, MenuFragment.newInstance(), null)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> {
                supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            R.id.action_search -> {
//                supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//                replaceFragment(R.id.mainContainer, SearchFragment.newInstance(), null)
            }
            R.id.action_profile -> {
//                supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
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
}
