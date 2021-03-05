package com.asantherrera.savemoney365days.ui.saving

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.asantherrera.savemoney365days.R
import com.asantherrera.savemoney365days.databinding.FragmentSavingBinding
import com.asantherrera.savemoney365days.ui.about.InfoActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SavingFragment : Fragment() {

    private lateinit var homeViewModel: SavingViewModel
    private lateinit var _binding: FragmentSavingBinding

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSavingBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(SavingViewModel::class.java)
        homeViewModel.observeAllSavings(requireContext(), viewLifecycleOwner)

        binding.buttonChoose.setOnClickListener {
            homeViewModel.onLuckyBtnClicked()
        }

        binding.buttonSave.setOnClickListener {
            binding.imageFireworks.visibility = View.VISIBLE
            homeViewModel.onSaveBtnClicked(requireContext())
            launchCalendarAnimation()
        }

        binding.buttonAbout.setOnClickListener {
            startActivity(Intent(requireContext(), InfoActivity::class.java))
        }



        homeViewModel.result.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.textResult.text = it
                if (it.toIntOrNull() == null) {
                    binding.buttonSave.isEnabled = false
                } else {
                    binding.buttonSave.isEnabled = true
                    binding.imageFireworks.visibility = View.INVISIBLE
                }
            }
        })

        homeViewModel.remaining.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.textRemaining.text = it
            }
        })
    }


    fun launchCalendarAnimation() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.calendarAnimation.visibility = View.VISIBLE
            binding.calendarAnimation.setAnimation(R.raw.calendar)
            binding.calendarAnimation.playAnimation()
            delay(1100)
            binding.calendarAnimation.visibility = View.GONE
        }
    }
}