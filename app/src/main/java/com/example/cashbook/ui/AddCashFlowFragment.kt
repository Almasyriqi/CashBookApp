package com.example.cashbook.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.cashbook.CashBookRepository
import com.example.cashbook.R
import com.example.cashbook.database.CashBookDatabase
import com.example.cashbook.databinding.FragmentAddCashFlowBinding
import com.example.cashbook.viewModel.AddCashViewModel
import com.example.cashbook.viewModel.AddCashViewModelFactory
import java.util.*


class AddCashFlowFragment : Fragment() {
    val args : AddCashFlowFragmentArgs by navArgs()
    private lateinit var addCashFlowViewModel: AddCashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddCashFlowBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_cash_flow, container, false
        )

        val status = args.status
        if(status == 1){
            binding.tvTitle.setText("Tambah Pemasukan")
            binding.tvTitle.setTextColor(Color.GREEN)
        } else {
            binding.tvTitle.setText("Tambah Pengeluaran")
            binding.tvTitle.setTextColor(Color.RED)
        }

        val application = requireNotNull(this.activity).application

        val dao = CashBookDatabase.getInstance(application).userDatabaseDao

        val daoCash = CashBookDatabase.getInstance(application).cashFlowDatabaseDao

        val repository = CashBookRepository(dao, daoCash)

        val factory = AddCashViewModelFactory(repository, application, status)

        addCashFlowViewModel = ViewModelProvider(this, factory).get(AddCashViewModel::class.java)

        binding.addCashViewModel = addCashFlowViewModel

        binding.lifecycleOwner = this

        addCashFlowViewModel.navigatetoHome.observe(viewLifecycleOwner, {hasFinished->
            if(hasFinished == true){
                Log.i("MYTAG","insidi observe")
                navigateHome()
                addCashFlowViewModel.doneNavigatingHome()
            }
        })

        binding.dateField.setOnClickListener() {
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            // at last we are calling show
            // to display our date picker dialog.
            context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    { view, year, monthOfYear, dayOfMonth ->
                        val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        binding.dateField.setText(dat)
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
            }?.show()
        }

        addCashFlowViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Tidak dapat simpan data, pastikan input terisi dengan benar!", Toast.LENGTH_LONG).show()
                addCashFlowViewModel.donetoast()
            }
        })

        addCashFlowViewModel.successtoast.observe(viewLifecycleOwner, Observer { hasSuccess->
            if(hasSuccess==true){
                Toast.makeText(requireContext(), "Success Simpan Data", Toast.LENGTH_LONG).show()
                addCashFlowViewModel.donetoast()
            }
        })

        return binding.root
    }

    private fun navigateHome() {
        Log.i("MYTAG","toHome")
        val action = AddCashFlowFragmentDirections.actionAddCashFlowFragmentToHomeFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
}