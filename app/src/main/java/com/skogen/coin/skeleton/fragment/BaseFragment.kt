package com.skogen.coin.skeleton.fragment

import android.os.Bundle
import android.os.Build
import android.content.Context

import android.support.annotation.LayoutRes
import android.support.annotation.IdRes

import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.skogen.coin.skeleton.activity.BaseActivity
import com.skogen.coin.skeleton.presentation.BasePresenter
import com.skogen.coin.skeleton.presentation.BaseView

/*
 * Created by Koba on 26.10.17.
 * Contact the developer - cosmicgate97@gmail.com
 */

/**
 * Class designed as parent for all Fragments created in project.
 *
 * @param <A> parent activity for created fragment. Info: [activity]
 * @param <P> presenter for created fragment. The instance should be passed in [createPresenter()] method
 */
abstract class BaseFragment<A : BaseActivity<*>, P : BasePresenter> : Fragment(), BaseView {

    protected var presenter: P? = null

    /**
     * You this instead of [android.app.Fragment.getActivity]
     */
    protected lateinit var activity: A

    private var rootView: View? = null

    /**
     * Place your layout resource as return parameter
     *
     * @return resourceId of layout which designed for current fragment
     */
    @get:LayoutRes
    protected abstract val layoutId: Int

    /**
     * You should create an instance of your presenter here. After that you can use [presenter] everywhere in created fragment
     *
     * @return an instance of presenter for created fragment
     */
    protected abstract fun createPresenter(): P

    /**
     * Initialize all views here.
     * This method do the same as [android.app.Fragment.onCreateView]
     *
     * @param rootView already inflated view
     */
    protected abstract fun initViews(rootView: View?)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        @Suppress("UNCHECKED_CAST")
        activity = getActivity() as A
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = createPresenter()
        return inflater?.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) = initViews(view)

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
        fragmentManager.beginTransaction().replace(containerId, fragment, tag).addToBackStack(tag)
            .commitAllowingStateLoss()
    }

    override fun showProgressView() = activity.showProgressView()

    override fun hideProgressView() = activity.hideProgressView()

}
