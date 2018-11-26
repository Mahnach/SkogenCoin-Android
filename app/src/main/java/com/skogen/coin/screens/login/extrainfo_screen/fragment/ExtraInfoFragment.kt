package com.skogen.coin.screens.login.extrainfo_screen.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.skogen.coin.R
import com.skogen.coin.models.UserModel
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.presenter.ExtraInfoPresenter
import com.skogen.coin.screens.login.extrainfo_screen.fragment.presentation.view.ExtraInfoView
import com.skogen.coin.skeleton.fragment.BaseFragment
import com.skogen.coin.utils.Validator
import com.vicpin.krealmextensions.queryFirst
import kotlinx.android.synthetic.main.fragment_extra_info.*
import android.view.WindowManager
import com.skogen.coin.screens.login.add_payment_screen.fragment.AddPaymentFragment
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream


class ExtraInfoFragment : BaseFragment<LoginActivity, ExtraInfoPresenter>(), ExtraInfoView,
        View.OnClickListener {

    private var userPhoto: File? = null
    private var userModel: UserModel? = null

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
        userModel = UserModel().queryFirst()
        extraInfoTvName.text = getString(R.string.name_placeholder, userModel?.name, userModel?.surname)
        initEt()
        extraInfoBtn.setOnClickListener(this)
        extraInfoCivAvatar.setOnClickListener(this)
        getActivity()?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun initEt() {
        extraInfoEtPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                showOrHideBtn()
            }
        })
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
                            userPhoto, extraInfoPin.text.toString()
                    )
                }
                else -> Unit
            }
        }
    }

    override fun showOkResult() {
        hideProgressView()
        getActivity()?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        addFragment(R.id.loginContainer, AddPaymentFragment.newInstance(), null)
//        activity.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//        addFragment(R.id.loginContainer, LoginPhoneFragment.newInstance(), null)
    }

    private fun showOrHideBtn() {
        extraInfoBtn.isEnabled = !extraInfoPin.text.toString().isNullOrEmpty()
                && extraInfoPin.text.toString().length >= 4 && !extraInfoEtPhone.text.toString().isNullOrEmpty()
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

                val stream = context?.contentResolver?.openInputStream(data.data)

                userPhoto = File(context?.cacheDir, data.data.lastPathSegment+".png")
                val output = FileOutputStream(userPhoto)
                stream?.copyTo(output, 4 * 1024)

//                val compressionRatio = 2 //1 == originalImage, 2 = 50% compression, 4=25% compress
//                try {
//                    val bitmap = BitmapFactory.decodeFile(userPhoto!!.path)
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, compressionRatio, FileOutputStream(userPhoto))
//                } catch (t: Throwable) {
//                    Log.e("ERROR", "Error compressing file." + t.toString())
//                    t.printStackTrace()
//                }

                Timber.e("${userPhoto?.absolutePath}")
                Timber.e("${userPhoto?.length()}")

                extraInfoCivAvatar.borderColor = resources.getColor(R.color.colorPrimary)
                Glide.with(this).load(data.data).into(extraInfoCivAvatar)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
