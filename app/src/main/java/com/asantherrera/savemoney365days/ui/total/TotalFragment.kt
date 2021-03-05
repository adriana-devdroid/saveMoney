package com.asantherrera.savemoney365days.ui.total

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.asantherrera.savemoney365days.R
import com.asantherrera.savemoney365days.database.SaveMoneyDatabase
import com.asantherrera.savemoney365days.databinding.FragmentTotalBinding

class TotalFragment : Fragment() {

    private lateinit var totalViewModel: TotalViewModel
    private lateinit var _binding: FragmentTotalBinding

    private val totalAdapter = TotalAdapter(mutableListOf())
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        totalViewModel = ViewModelProvider(this).get(TotalViewModel::class.java)
        _binding = FragmentTotalBinding.inflate(inflater, container, false)
        val root = binding.root


        totalViewModel.showAll(viewLifecycleOwner, requireContext())

        setUpRecyclerView(totalAdapter)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        totalViewModel.totalList.observe(viewLifecycleOwner, Observer { items ->
            if (items != null) {
                totalAdapter.items = items
                totalAdapter.notifyDataSetChanged()

                totalViewModel.total.value = items.sumBy { it.id }

            } else {
                Toast.makeText(context, "OCURRIO UN ERROR AL OBTENER LOS DATOS", Toast.LENGTH_LONG)
                    .show()
            }
        })

        totalViewModel.totalListByAmount.observe(viewLifecycleOwner, Observer { items ->
            if (items != null) {
                totalAdapter.items = items
                totalAdapter.notifyDataSetChanged()
            }
        })

        totalViewModel.totalListByDate.observe(viewLifecycleOwner, Observer { items ->
            if (items != null) {
                totalAdapter.items = items
                totalAdapter.notifyDataSetChanged()
            }
        })

        totalViewModel.total.observe(viewLifecycleOwner, Observer {
            val formatTotal = "$ $it "
            binding.total.text = formatTotal
        })

        binding.buttonAmountFilter.setOnClickListener {
            totalViewModel.getListByAmount()
        }
        binding.buttonDateFilter.setOnClickListener {
            totalViewModel.getListByDate()
        }
    }

    private fun setUpRecyclerView(totalAdapter: TotalAdapter) {
        binding.recyclerTotal.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = totalAdapter
        }
    }
}