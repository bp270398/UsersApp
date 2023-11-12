package hr.algebra.nasa.factory

import android.content.Context
import hr.algebra.nasa.dao.UsersSqlHelper

fun getUsersRepository(context: Context?) = UsersSqlHelper(context)