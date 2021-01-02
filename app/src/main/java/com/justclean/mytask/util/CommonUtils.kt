package com.justclean.mytask.util

import android.app.Activity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object CommonUtils{
    fun alertDialog(activity: Activity, data: String, yes: String, no: String, listerner: BasicClickListener) {

        val builder = MaterialAlertDialogBuilder(activity)
        builder.setTitle("Are you sure?")
        builder.setMessage(data)
        builder.setPositiveButton(yes) { dialog, which ->
            listerner.onYesClick("")
            dialog.dismiss()
        }
        builder.setNegativeButton(no) { dialog, which ->
            listerner.onNoClick()
            dialog.dismiss()
        }

        builder.show()
    }
}