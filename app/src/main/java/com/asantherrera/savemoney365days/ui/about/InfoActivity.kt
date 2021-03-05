package com.asantherrera.savemoney365days.ui.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asantherrera.savemoney365days.R
import com.asantherrera.savemoney365days.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

}