package com.abhishekslal.cryptovalidatore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.abhishekslal.cryptovalidatore.databinding.ActivityValidationBinding

class ValidationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityValidationBinding
    private lateinit var coinCode: String
    private lateinit var decodedData: String

    private val viewModel: ValidationActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValidationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        coinCode = intent.getStringExtra("coinCode").toString()
        decodedData = intent.getStringExtra("decodedData").toString()

        binding.decodedText.text = buildString {
            append("Decoded ")
            append(coinCode)
            append(" address:\n ")
            append(decodedData)
        }

        binding.validateBtn.setOnClickListener {
            if (coinCode == "ETH") displayValidText(viewModel.validateETH(decodedData))
            else displayValidText(viewModel.validateBTC(decodedData))
        }

        binding.shareBtn.setOnClickListener {
            if (coinCode == "ETH") shareAddress(viewModel.validateETH(decodedData))
            else shareAddress(viewModel.validateBTC(decodedData))
        }
    }

    private fun shareAddress(isValid: Boolean) {
        if (!isValid) {
            Toast.makeText(this, "Cannot share. Invalid $coinCode address.", Toast.LENGTH_LONG)
                .show()
            return
        }
        val text = "Here is a Valid $coinCode Address : \n\n$decodedData"
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share $coinCode Address")
        startActivity(shareIntent)
    }

    private fun displayValidText(isValid: Boolean) {
        val text = if (isValid) "a valid " else "not a valid "
        binding.validationMsg.text = buildString {
            append("This is ")
            append(text)
            append(coinCode)
            append(" address.").toString()
        }
    }
}