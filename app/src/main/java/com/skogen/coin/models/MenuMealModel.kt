package com.skogen.coin.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Koba on 22.11.2018.
 * cosmicgate97@gmail.com
 */

class MenuMealModel (@SerializedName("_id") var id: String?, var name: String?,
                     var prices: ArrayList<PriceModel>?, var photo: String?,
                     var description: String?) : Serializable
