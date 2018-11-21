package com.skogen.coin.screens.login.extrainfo_screen.fragment

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.WebView.HitTestResult.IMAGE_TYPE
import android.widget.Toast
import com.bumptech.glide.Glide
import com.pixplicity.easyprefs.library.Prefs
import com.skogen.coin.App.Companion.context
import com.skogen.coin.R
import com.skogen.coin.R.id.extraInfoBtn
import com.skogen.coin.R.id.extraInfoPin
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.presenter.ExtraInfoPresenter
import com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.view.ExtraInfoView
import com.skogen.coin.skeleton.fragment.BaseFragment
import com.skogen.coin.utils.ImageUtil
import com.skogen.coin.utils.Validator
import kotlinx.android.synthetic.main.fragment_extra_info.*
import timber.log.Timber

class ExtraInfoFragment : BaseFragment<LoginActivity, ExtraInfoPresenter>(), ExtraInfoView,
        View.OnClickListener {

    private var bitmapImage: Bitmap? = null

    override val layoutId: Int
        get() = R.layout.fragment_extra_info

    override fun createPresenter(): ExtraInfoPresenter {
        return ExtraInfoPresenter(this)
    }

    companion object {
        fun newInstance(): ExtraInfoFragment {
            val args = Bundle()
            val fragment = ExtraInfoFragment()
            fragment.arguments = args
            return fragment
        }

        private const val READ_EXTERNAL_STORAGE = 11
        private const val PICK_IMAGE = 12
        private const val IMAGE_TYPE = "image/*"
    }

    override fun initViews(rootView: View?) {
        initEt()
        extraInfoBtn.setOnClickListener(this)
        extraInfoCivAvatar.setOnClickListener(this)
    }

    private fun initEt() {
        extraInfoPin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                showOrHideBtn()
            }
        })
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v) {
                extraInfoCivAvatar -> checkPermission()
                extraInfoBtn -> {
                    if (!extraInfoEtPhone.text.toString().isNullOrEmpty()) {
                        if (!Validator.phoneValidator(extraInfoEtPhone.text.toString())) {
                            Toast.makeText(context, getString(R.string.error_invalid_phone), Toast.LENGTH_SHORT).show()
                            return
                        }
                    }

                    if (!extraInfoEtEmail.text.toString().isNullOrEmpty()) {
                        if (!Validator.emailValidator(extraInfoEtEmail.text.toString())) {
                            Toast.makeText(context, getString(R.string.error_invalid_email), Toast.LENGTH_SHORT).show()
                            return
                        }
                    }

                    presenter?.saveUser(
                            extraInfoEtPhone.text.toString(), extraInfoEtEmail.text.toString(),
                            ImageUtil.convert(bitmapImage), extraInfoPin.text.toString()
                    )
                }
                else -> Unit
            }
        }
    }

    override fun showOkResult() {
        Toast.makeText(context, "SENDED", Toast.LENGTH_SHORT).show()
    }

    private fun showOrHideBtn() {
        extraInfoBtn.isEnabled = !extraInfoPin.text.toString().isNullOrEmpty()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun checkPermission() {
        if (context?.let { ActivityCompat.checkSelfPermission(it, android.Manifest.permission.READ_EXTERNAL_STORAGE) }
                != PackageManager.PERMISSION_GRANTED && context?.let {
                    ActivityCompat.checkSelfPermission(it, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE
            )
        } else {
            openPicker()
        }
    }

    private fun openPicker() {
        val intent = Intent().apply {
            type = IMAGE_TYPE
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == READ_EXTERNAL_STORAGE) {
            if (permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && !shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
            ) {
                openPicker()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PICK_IMAGE -> {
                if (data == null)
                    return

                if (bitmapImage != null) {
                    bitmapImage?.recycle()
                }

                val stream = context?.contentResolver?.openInputStream(data.data)

                bitmapImage = BitmapFactory.decodeStream(stream)
                stream?.close()

                extraInfoCivAvatar.borderColor = resources.getColor(R.color.colorPrimary)
                Glide.with(this).load(data?.data).into(extraInfoCivAvatar)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
