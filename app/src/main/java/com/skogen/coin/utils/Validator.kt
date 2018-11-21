package com.skogen.coin.utils

import java.util.regex.Pattern

object Validator {

    private const val MAIL_PATTERN = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" +
            "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" +
            "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." +
            "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" +
            "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" +
            "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    private const val PHONE_PATTERN = "^[+][0-9]{9,15}$"

    fun emailValidator(email: String): Boolean {
        val regExpn = MAIL_PATTERN

        val inputStr = email

        val pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)

        return matcher.matches()

    }

    fun phoneValidator(phone: String): Boolean {
        val regExpn = PHONE_PATTERN

        val inputStr = phone

        val pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)

        return matcher.matches()

    }
}