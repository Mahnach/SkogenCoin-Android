package com.skogen.coin.extensions

import android.app.Fragment
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skogen.coin.App

/**
 * Created by Koba on 26.10.17.
 * Contact the developer - cosmicgate97@gmail.com
 */

//GLOBAL
val appContext: Context = App.context

val appRes: Resources = App.context.resources

//LOGS
fun <A : Any> A.log(text: String = "", throwable: Throwable? = null) = apply { Log.e(this.toString(), text, throwable) }

//VIEWS
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

//SPANNABLE
fun String.withHtml(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

//OTHER
inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

//inline fun <reified T : Fragment> newInstance(vararg params: Pair<String, Any>): T = T::class.java.newInstance().apply {
//    arguments = bundleOf(*params) //To use this add Anko library
//}
