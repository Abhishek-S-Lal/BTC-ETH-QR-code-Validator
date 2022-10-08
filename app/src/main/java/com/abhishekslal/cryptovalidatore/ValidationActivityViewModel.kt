package com.abhishekslal.cryptovalidatore

import androidx.lifecycle.ViewModel

class ValidationActivityViewModel: ViewModel() {
    fun validateBTC(address : String):Boolean{
        val regex = Regex("^[1][a-km-zA-HJ-NP-Z1-9]{24,33}\$")
        return address.trim().matches(regex)
    }

    fun validateETH(address : String): Boolean{
        val regex = Regex("^[0x]{2}[a-fA-F0-9]{1,60}\$")
        return address.trim().matches(regex)
    }
}