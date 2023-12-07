package com.example.pilleat.all.rvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pilleat.all.response.AllPillInfoResponse
import com.example.pilleat.databinding.ItemPillinfoBinding

class AllPillInfoRVAdapter(val context: Context, val result: AllPillInfoResponse): RecyclerView.Adapter<AllPillInfoRVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemPillinfoBinding): RecyclerView.ViewHolder(binding.root) {
        val essntlItemName: TextView = binding.itemPillInfoNameTv
        val medEfficacy: TextView = binding.itemPillInfoContentsTv
        val appointDate: TextView = binding.itemPillInfoDateTv
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPillinfoBinding = ItemPillinfoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = result.response.body.items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.essntlItemName.text = result.response.body.items[position].item.ESSNTL_ITEM_NAME
        holder.medEfficacy.text = result.response.body.items[position].item.MED_EFFICACY
        holder.appointDate.text = result.response.body.items[position].item.APPOINT_DATE
    }
}