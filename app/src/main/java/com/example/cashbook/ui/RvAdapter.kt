package com.example.cashbook.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cashbook.R
import com.example.cashbook.database.CashFlowEntity
import com.example.cashbook.databinding.ListDetailBinding
import java.text.NumberFormat
import java.util.*

class RvAdapter(private val cashFlowList : List<CashFlowEntity>): RecyclerView.Adapter<CashViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListDetailBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_detail,parent,false)
        return CashViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cashFlowList.size
    }

    override fun onBindViewHolder(holder: CashViewHolder, position: Int) {
        holder.bind(cashFlowList[position])

    }
}

class CashViewHolder(private val binding :ListDetailBinding ):RecyclerView.ViewHolder(binding.root){

    fun bind(cashFlow : CashFlowEntity){
        var nominal = ""
        if(cashFlow.status == "pemasukan"){
            nominal = "[+] " + cashFlow.nominal?.let { rupiah(it.toDouble()) }
            binding.nominalTextView.setText(nominal)
            binding.iconFlow.setImageResource(R.drawable.`in`)
        } else {
            nominal = "[-] " + cashFlow.nominal?.let { rupiah(it.toDouble()) }
            binding.iconFlow.setImageResource(R.drawable.out)
        }
        binding.nominalTextView.setText(nominal)
        binding.descriptionTextView.setText(cashFlow.description)
        binding.dateTextView.setText(cashFlow.date)
    }

    fun rupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }
}