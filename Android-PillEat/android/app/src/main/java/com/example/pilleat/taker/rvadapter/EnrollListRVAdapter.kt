package com.example.pilleat.taker.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pilleat.databinding.ItemEnrollBinding
import com.example.pilleat.taker.table.EnrollPill

class EnrollListRVAdapter(private var drugList: ArrayList<EnrollPill>) : RecyclerView.Adapter<EnrollListRVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemEnrollBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(drug: EnrollPill) {
            binding.enrollNameTv.text = drug.pill_name
            binding.enrollCategoryTv.text = drug.pill_category
            binding.enrollTimeTv.text = drug.pill_time
            binding.enrollVolumnTv.text = drug.pill_volumn
        }
    }

    interface MyItemClickListener {
        fun onItemClick(drug: EnrollPill)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemEnrollBinding = ItemEnrollBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = drugList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(drugList[position])
        holder.binding.enrollItemLo.setOnClickListener { mItemClickListener.onItemClick(drugList[position]) }
    }
}