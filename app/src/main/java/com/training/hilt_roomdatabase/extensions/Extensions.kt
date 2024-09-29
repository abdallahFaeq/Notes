package com.training.hilt_roomdatabase.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController


fun Fragment.navigate(
    action : NavDirections
){
    findNavController().navigate(action)
}