package com.example.cashbook.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.cashbook.CashBookRepository
import com.example.cashbook.R
import com.example.cashbook.database.CashBookDatabase
import com.example.cashbook.databinding.FragmentHomeBinding
import com.example.cashbook.viewModel.HomeViewModel
import com.example.cashbook.viewModel.HomeViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = CashBookDatabase.getInstance(application).userDatabaseDao

        val repository = CashBookRepository(dao)

        val factory = HomeViewModelFactory(repository, application)

        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        binding.homeViewModel = homeViewModel

        binding.lifecycleOwner = this



        homeViewModel.navigatetoSetting.observe(viewLifecycleOwner, {hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                navigateSetting()
                homeViewModel.doneNavigatingSetting()
            }
        })
        return binding.root
    }

    private fun navigateSetting() {
        Log.i("MYTAG","toSetting")
        val action = HomeFragmentDirections.actionHomeFragmentToSettingFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
}