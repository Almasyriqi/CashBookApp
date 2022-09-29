package com.example.cashbook.ui

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cashbook.database.CashBookDatabase
import com.example.cashbook.CashBookRepository
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cashbook.R
import com.example.cashbook.viewModel.DetailCashViewModel
import com.example.cashbook.databinding.FragmentDetailCashBinding
import com.example.cashbook.viewModel.DetailCashViewModelFactory

class DetailCashFragment : Fragment() {

    private lateinit var detailCashFlowViewModel: DetailCashViewModel
    private lateinit var binding: FragmentDetailCashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail_cash, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = CashBookDatabase.getInstance(application).userDatabaseDao

        val daoCash = CashBookDatabase.getInstance(application).cashFlowDatabaseDao

        val repository = CashBookRepository(dao, daoCash)

        val factory = DetailCashViewModelFactory(repository, application)

        detailCashFlowViewModel =
            ViewModelProvider(this, factory).get(DetailCashViewModel::class.java)

        binding.detailCashLayout = detailCashFlowViewModel

        binding.lifecycleOwner = this

        detailCashFlowViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                val action = DetailCashFragmentDirections.actionDetailsCashFragmentToHomeFragment()
                NavHostFragment.findNavController(this).navigate(action)
                detailCashFlowViewModel.doneNavigating()
            }
        })

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        binding.detailsRecyclerView.layoutManager = LinearLayoutManager(this.context)
        displayCashFlowList()
    }


    private fun displayCashFlowList() {
        Log.i("MYTAG", "Inside ...detailCashFlow..Fragment")
        detailCashFlowViewModel.cashFlowList.observe(viewLifecycleOwner, Observer {
            binding.detailsRecyclerView.adapter = RvAdapter(it)
        })

    }
}