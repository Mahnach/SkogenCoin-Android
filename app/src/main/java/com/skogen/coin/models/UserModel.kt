package com.skogen.coin.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

/**
 * Created by Koba on 21.11.2018.
 * cosmicgate97@gmail.com
 */

open class UserModel(@PrimaryKey var id: Long? = null, var name: String = "", var surname: String = "", var photo: String? = null, var phone: String? = null,
                      var email: String? = null, var pin: String? = null): Serializable, RealmObject()