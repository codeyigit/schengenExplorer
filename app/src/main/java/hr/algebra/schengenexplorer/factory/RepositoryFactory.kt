package hr.algebra.schengenexplorer.factory

import android.content.Context
import hr.algebra.schengenexplorer.dao.SchengenSqlHelper

fun getSchengenRepository(context: Context?) = SchengenSqlHelper(context)