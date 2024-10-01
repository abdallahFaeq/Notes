package com.training.hilt_roomdatabase.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Fragment.navigate(
    action : NavDirections
){
    findNavController().navigate(action)
}

fun Fragment.getCurrentDateTime():String{
    return SimpleDateFormat(
        "EEEE, dd MMMM yyyy HH:mm a",
        Locale.getDefault()
    ).format(Date())
}

fun Fragment.showToast(context:Context, message:String){
    Toast.makeText(context,
        message,
        Toast.LENGTH_LONG).show()
}