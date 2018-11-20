package com.skogen.coin.skeleton.activity

import com.skogen.coin.R
import android.support.v7.app.AppCompatActivity
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.view.View

import android.support.annotation.LayoutRes
import android.support.annotation.IdRes

import android.view.inputmethod.InputMethodManager
import com.skogen.coin.extensions.consume
import com.skogen.coin.extensions.gone
import com.skogen.coin.extensions.visible
import com.skogen.coin.skeleton.fragment.BaseFragment
import com.skogen.coin.skeleton.presentation.BaseView
import com.skogen.coin.skeleton.presentation.BasePresenter
import com.skogen.coin.utils.KeyboardController

/*
 * Created by Koba on 26.10.17.
 * Contact the developer - cosmicgate97@gmail.com
 */

/**
 * Class designed as parent for all Activities created in project.
 *
 * @param <P> presenter for created activity. The instance should be passed in [createPresenter()] method
 */
abstract class BaseActivity<P : BasePresenter> : AppCompatActivity(), BaseView, KeyboardController {

    protected lateinit var presenter: P

    /**
     * Place your layout resource as return parameter
     *
     * @return resourceId of layout which designed for current fragment
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    /**
     * You should create an instance of your presenter here. After that you can use [presenter] everywhere in created activity
     *
     * @return an instance of presenter for created activity
     */
    protected abstract fun createPresenter(): P

    /**
     * Initialize all views here.
     * This method do the same as [android.app.Activity.onCreate]
     */
    protected abstract fun initViews()

    private lateinit var progressView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        progressView = generateProgressView()
        addContentView(
            progressView,
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
        presenter = createPresenter()
        initViews()
    }

    /**
     * Replaces existing fragment with allowing state loss
     *
     * @param containerId view where fragment will be replaced
     * @param fragment    new fragment
     * @param tag         for new fragment
     */
    protected fun replaceFragment(@IdRes containerId: Int, fragment: BaseFragment<*, *>, tag: String?) {
        fragmentManager.beginTransaction().replace(containerId, fragment, tag).commitAllowingStateLoss()
    }

    /**
     * Adds fragment into backstack above existing fragment allowing state loss
     *
     * @param containerId view where fragment will be added
     * @param fragment    new fragment
     * @param tag         for new fragment
     */
    protected fun addFragment(@IdRes containerId: Int, fragment: BaseFragment<*, *>, tag: String?) {
        fragmentManager.beginTransaction().add(containerId, fragment, tag).addToBackStack(null)
            .commitAllowingStateLoss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        // Respond to the action bar's Up/Home button
        android.R.id.home -> consume { closeKeyboard() }
        else -> super.onOptionsItemSelected(item)
    }

    override fun showProgressView() {
        runOnUiThread {
            progressView.visible()
        }
    }

    override fun hideProgressView() {
        runOnUiThread {
            progressView.gone()
        }
    }

    override fun closeKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    /**
     * Creates progress view. You can implement your own progress view. <br></br>
     * Just replace first parameter of inflate() method on your own implementation
     *
     * @return generated progress view
     */
    private fun generateProgressView(): View = window.layoutInflater.inflate(R.layout.widget_progress_view, null, false)

}
