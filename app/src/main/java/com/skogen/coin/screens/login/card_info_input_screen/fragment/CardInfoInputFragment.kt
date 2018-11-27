package com.skogen.coin.screens.login.card_info_input_screen.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.skogen.coin.R
import com.skogen.coin.models.UserModel
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.login.card_info_input_screen.fragment.presentation.presenter.CardInfoInputPresenter
import com.skogen.coin.screens.login.card_info_input_screen.fragment.presentation.view.CardInfoInputView
import com.skogen.coin.screens.main.MainActivity
import com.skogen.coin.skeleton.fragment.BaseFragment
import com.stripe.android.model.Card
import com.vicpin.krealmextensions.queryFirst
import kotlinx.android.synthetic.main.fragment_card_info_input.*

class CardInfoInputFragment : BaseFragment<LoginActivity, CardInfoInputPresenter>(), CardInfoInputView, View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_card_info_input

    override fun createPresenter(): CardInfoInputPresenter {
        return CardInfoInputPresenter(this)
    }

    companion object {
        fun newInstance(): CardInfoInputFragment {
            val args = Bundle()
            val fragment = CardInfoInputFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun initViews(rootView: View?) {
        val user = UserModel().queryFirst()
        cardInfoTvUser.text = getString(R.string.name_placeholder, user?.name, user?.surname)

        cardInfoIvCamera.setOnClickListener(this)
        cardInfoBtn.setOnClickListener(this)
        initEts()
    }

    private fun initEts() {
        val cardNumberListener = MaskedTextChangedListener.installOn(
                cardInfoEtCardNumber,
                "[0000] [0000] [0000] [0000]",
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
                        checkBtnEnabling()
                    }
                })
        val expDateListener = MaskedTextChangedListener.installOn(
                cardInfoEtExpDate,
                "[00]/[00]",
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
                        checkBtnEnabling()
                    }
                })
        cardInfoEtCVC.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkBtnEnabling()
            }
        })

        cardInfoEtCardHolder.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkBtnEnabling()
            }
        })
    }

    private fun checkBtnEnabling() {
        if (!cardInfoEtCardNumber.text.isNullOrEmpty() && cardInfoEtCardNumber.text.toString().length >= 19 &&
                !cardInfoEtExpDate.text.isNullOrEmpty() && cardInfoEtExpDate.text.toString().length >= 5 &&
                !cardInfoEtCVC.text.isNullOrEmpty() && cardInfoEtCVC.text.toString().length >= 3 &&
                !cardInfoEtCardHolder.text.isNullOrEmpty()) {
            cardInfoTvRight.visibility = View.VISIBLE
            cardInfoBtn.isEnabled = true
        }
        else {
            cardInfoTvRight.visibility = View.GONE
            cardInfoBtn.isEnabled = false
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when(v) {
                cardInfoIvCamera -> {}
                cardInfoBtn -> { presenter?.createCard(cardInfoEtCardNumber.text.toString(), cardInfoEtExpDate.text.toString(),
                    cardInfoEtCVC.text.toString(), cardInfoEtCardHolder.text.toString())
                }
                else -> Unit
            }
        }
    }

    override fun showErrorCardValidation() {
        Toast.makeText(context, getString(R.string.error_card_credentials), Toast.LENGTH_SHORT).show()
    }

    override fun showOkResult() {
        startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }
}
