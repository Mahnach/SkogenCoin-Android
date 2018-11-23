package com.skogen.coin.models

import com.google.gson.annotations.SerializedName


/**
 * Created by Koba on 22.11.2018.
 * cosmicgate97@gmail.com
 */
class MenuMealModel (@SerializedName("_id") var id: String?, var name: String?,
                     var price: Int?, var volume: String?, var type: String?, var photo: String?,
                     var desc: String?)
