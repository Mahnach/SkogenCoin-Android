package com.skogen.coin.screens.main.profile_screen.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.pixplicity.easyprefs.library.Prefs
import com.skogen.coin.R
import com.skogen.coin.models.UserModel
import com.skogen.coin.screens.main.MainActivity
import com.skogen.coin.screens.main.profile_screen.fragment.presentation.presenter.ProfilePresenter
import com.skogen.coin.screens.main.profile_screen.fragment.presentation.view.ProfileView
import com.skogen.coin.screens.splash.SplashActivity
import com.skogen.coin.skeleton.fragment.BaseFragment
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import kotlinx.android.synthetic.main.fragment_extra_info.*
import kotlinx.android.synthetic.main.fragment_profile.*
import timber.log.Timber

class ProfileFragment : BaseFragment<MainActivity, ProfilePresenter>(), ProfileView {

    override val layoutId: Int
        get() = R.layout.fragment_profile


    override fun createPresenter(): ProfilePresenter {
        return ProfilePresenter(this)
    }

    companion object {
        fun newInstance(): ProfileFragment {
            val args = Bundle()
            val fragment = ProfileFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun initViews(rootView: View?) {
        val user = UserModel().queryFirst()

        if (!user?.photo.isNullOrEmpty())
            Glide.with(this).load(user?.photo).into(profileCivAvatar)

        profileTvName.text = getString(R.string.name_placeholder, user?.name, user?.surname)
        profileTvPhoneValue.text = user?.phone
        profileTvEmailValue.text = user?.email

        profileTvLogout.setOnClickListener {
            UserModel().deleteAll()
            Prefs.clear()
            startActivity(Intent(activity, SplashActivity::class.java))
            activity.finish()
        }
    }
}
