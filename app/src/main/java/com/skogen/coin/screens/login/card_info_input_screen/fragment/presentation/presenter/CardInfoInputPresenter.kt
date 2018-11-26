package com.skogen.coin.screens.login.card_info_input_screen.fragment.presentation.presenter

import com.skogen.coin.screens.login.card_info_input_screen.fragment.presentation.view.CardInfoInputView
import com.skogen.coin.skeleton.presentation.BasePresenter
import com.stripe.android.model.Card

class CardInfoInputPresenter(override val view: CardInfoInputView) : BasePresenter() {

    fun init() {}

    fun createCard(cardNumber: String, expDate: String, cvc: String, cardHolder: String) {
        var card: Card = Card.Builder(cardNumber, expDate.substringBefore("/").toInt(),
                expDate.substringAfter("/").toInt(), cvc).name(cardHolder).build()

        if (!card.validateCard()){
            view.showErrorCardValidation()
        } else view.showOkResult()
    }
}
