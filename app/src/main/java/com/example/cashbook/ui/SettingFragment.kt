package com.example.cashbook.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.cashbook.CashBookRepository
import com.example.cashbook.R
import com.example.cashbook.database.CashBookDatabase
import com.example.cashbook.databinding.FragmentSettingBinding
import com.example.cashbook.viewModel.SettingViewModel
import com.example.cashbook.viewModel.SettingViewModelFactory

class SettingFragment : Fragment() {
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSettingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = CashBookDatabase.getInstance(application).userDatabaseDao

        val daoCash = CashBookDatabase.getInstance(application).cashFlowDatabaseDao

        val repository = CashBookRepository(dao, daoCash)

        val factory = SettingViewModelFactory(repository, application)

        settingViewModel = ViewModelProvider(this, factory).get(SettingViewModel::class.java)

        binding.settingViewModel = settingViewModel

        binding.lifecycleOwner = this

        settingViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Current Password Wrong", Toast.LENGTH_SHORT).show()
                settingViewModel.donetoast()
            }
        })

        settingViewModel.successtoast.observe(viewLifecycleOwner, Observer { hasSuccess->
            if(hasSuccess==true){
                Toast.makeText(requireContext(), "Success Update Password, please login again", Toast.LENGTH_LONG).show()
                navigateLogin()
                settingViewModel.donetoast()
            }
        })

        settingViewModel.navigatetoHome.observe(viewLifecycleOwner, {hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                navigateHome()
                settingViewModel.doneNavigatingHome()
            }
        })
        return binding.root
    }

    private fun navigateHome() {
        Log.i("MYTAG","toHome")
        val action = SettingFragmentDirections.actionSettingFragmentToHomeFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun navigateLogin() {
        Log.i("MYTAG","logout")
        val action = SettingFragmentDirections.actionSettingFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
}