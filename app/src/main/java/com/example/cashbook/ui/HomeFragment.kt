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
import com.example.cashbook.database.CashFlowEntity
import com.example.cashbook.databinding.FragmentHomeBinding
import com.example.cashbook.viewModel.HomeViewModel
import com.example.cashbook.viewModel.HomeViewModelFactory
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

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

        val daoCash = CashBookDatabase.getInstance(application).cashFlowDatabaseDao

        val repository = CashBookRepository(dao, daoCash)

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

        homeViewModel.navigatetoDetail.observe(viewLifecycleOwner, {hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                navigateDetail()
                homeViewModel.doneNavigatingDetail()
            }
        })

        homeViewModel.navigatetoPemasukan.observe(viewLifecycleOwner, {hasFinished->
            if(hasFinished == true){
                Log.i("MYTAG","insidi observe")
                navigateAddCashFlow(1)
                homeViewModel.doneNavigatingAddCashFlow()
            }
        })

        homeViewModel.navigatetoPengeluaran.observe(viewLifecycleOwner, {hasFinished->
            if(hasFinished == true){
                Log.i("MYTAG","insidi observe")
                navigateAddCashFlow(0)
                homeViewModel.doneNavigatingAddCashFlow()
            }
        })

        binding.textPemasukan.setText("Pemasukan: "+rupiah(repository.getSumCash("pemasukan")))
        binding.textPengeluaran.setText("Pengeluaran "+rupiah(repository.getSumCash("pengeluaran")))

        val tanggal = repository.getDate()
        var nominal_pemasukan = ArrayList<Double>()
        var nominal_pengeluaran = ArrayList<Double>()

        tanggal.forEach {
            var pemasukan = repository.getSumNominal(it, "pemasukan")
            if(pemasukan == null){
                pemasukan = 0.0
            }
            nominal_pemasukan.add(pemasukan)

            var pengeluaran = repository.getSumNominal(it, "pengeluaran")
            if(pengeluaran == null){
                pengeluaran = 0.0
            }
            nominal_pengeluaran.add(pengeluaran)
        }

        val lineEntry = ArrayList<Entry>()
        nominal_pemasukan.forEachIndexed{index, element->
            lineEntry.add(Entry(element.toFloat(), index))
        }

        val lineEntry1 = ArrayList<Entry>()
        nominal_pengeluaran.forEachIndexed { index, d ->
            lineEntry1.add(Entry(d.toFloat(), index))
        }

        val linePemasukan = LineDataSet(lineEntry, "Pemasukan")
        linePemasukan.color = resources.getColor(R.color.green)

        val linePengeluaran = LineDataSet(lineEntry1, "Pengeluaran")
        linePengeluaran.color = resources.getColor(R.color.red)

        val finaldataset = ArrayList<LineDataSet>()
        finaldataset.add(linePemasukan)
        finaldataset.add(linePengeluaran)

        val data = LineData(tanggal, finaldataset as List<ILineDataSet>?)

        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.lineChart.data = data
        binding.lineChart.animateXY(100, 500)

        return binding.root
    }

    private fun navigateSetting() {
        Log.i("MYTAG","toSetting")
        val action = HomeFragmentDirections.actionHomeFragmentToSettingFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun navigateDetail() {
        Log.i("MYTAG","toDetail")
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsCashFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun navigateAddCashFlow(status: Int){
        Log.i("MYTAG","toAddCashFlow")
        val action = HomeFragmentDirections.actionHomeFragmentToAddCashFlowFragment(status)
        NavHostFragment.findNavController(this).navigate(action)
    }

    fun rupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }
}