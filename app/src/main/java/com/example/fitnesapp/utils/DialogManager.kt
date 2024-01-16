package com.example.fitnesapp.utils

import android.app.AlertDialog
import android.content.Context
import com.example.fitnesapp.R

object DialogManager {
    fun showDialog(context: Context, mId: Int, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        var dialog: AlertDialog? = null
        builder.setTitle(R.string.note)
        builder.setMessage(mId)
        builder.setPositiveButton(R.string.reset) { _, _ ->
            listener.onClick()
            dialog?.dismiss() //чтобы закрылся
        }

        builder.setNegativeButton(R.string.back) { _, _ ->
            dialog?.dismiss() //чтобы закрылся

        }

        dialog = builder.create()
        dialog?.show()

    }


    interface Listener {
        fun onClick()
    }
}