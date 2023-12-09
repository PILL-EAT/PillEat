package com.example.pilleat.taker.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pilleat.databinding.ItemEnrollBinding
import com.example.pilleat.taker.response.EnrollPillListResponse
import com.example.pilleat.taker.response.PillList

class EnrollListRVAdapter(var result: EnrollPillListResponse) : RecyclerView.Adapter<EnrollListRVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemEnrollBinding) : RecyclerView.ViewHolder(binding.root) {
        val pill_name: TextView = binding.enrollNameTv
        val pill_category: TextView = binding.enrollCategoryTv
        val pill_date: TextView = binding.enrollDateTv
    }

    interface MyItemClickListener {
        fun onItemRemove(item: PillList)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemEnrollBinding = ItemEnrollBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = result.result.drugs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pill_name.text = result.result.drugs[position].name
        holder.pill_category.text = result.result.drugs[position].category
        holder.pill_date.text = result.result.drugs[position].date.toString()
        holder.binding.enrollRemoveBtn.setOnClickListener { mItemClickListener.onItemRemove(result.result.drugs[position]) }
    }
}