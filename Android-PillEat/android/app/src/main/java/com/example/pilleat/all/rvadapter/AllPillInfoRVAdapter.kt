package com.example.pilleat.all.rvadapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.pilleat.all.response.AllPillInfoResponse
import com.example.pilleat.all.response.ItemResult
import com.example.pilleat.databinding.ItemPillinfoBinding

class AllPillInfoRVAdapter(val context: Context, var result: AllPillInfoResponse): RecyclerView.Adapter<AllPillInfoRVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemPillinfoBinding): RecyclerView.ViewHolder(binding.root) {
        val itemName: TextView = binding.itemPillInfoNameTv
        val entpName: TextView = binding.itemPillInfoDateTv
        val efcyQesitm: TextView = binding.itemPillInfoContentsTv
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AllPillInfoRVAdapter.ViewHolder {
        val binding: ItemPillinfoBinding = ItemPillinfoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = result.body.totalCount

    override fun onBindViewHolder(holder: AllPillInfoRVAdapter.ViewHolder, position: Int) {
        holder.itemName.text = result.body.items[position].itemName
        holder.entpName.text = result.body.items[position].entpName
        holder.efcyQesitm.text = result.body.items[position].efcyQesitm
    }
}