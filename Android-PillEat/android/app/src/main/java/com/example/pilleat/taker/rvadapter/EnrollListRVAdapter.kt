package com.example.pilleat.taker.rvadapter

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pilleat.R
import com.example.pilleat.databinding.ItemEnrollBinding
import com.example.pilleat.manager.response.UserInfo
import com.example.pilleat.taker.response.Drugs
import com.example.pilleat.taker.response.EnrollPillListResponse
import com.example.pilleat.taker.response.PillList

class EnrollListRVAdapter() : RecyclerView.Adapter<EnrollListRVAdapter.ViewHolder>() {
    var result = ArrayList<PillList>()
    inner class ViewHolder(val binding: ItemEnrollBinding) : RecyclerView.ViewHolder(binding.root) {
        val pill_name: TextView = binding.enrollNameTv
        //val pill_kind: TextView = binding.enrollCategoryTv
        val alert_day: TextView = binding.enrollDateTv
        val alert_time: TextView = binding.enrollTimeTv
        val iot: TextView = binding.enrollIotTv
    }

    interface MyItemClickListener {
        fun onItemRemove(result: PillList)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    fun setData(pillList: ArrayList<PillList>) {
        this.result.clear()
        this.result.addAll(pillList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemEnrollBinding = ItemEnrollBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pill_name.text = result[position].name
        //holder.pill_kind.text = result[position].category
        holder.alert_time.text = result[position].time

        // 요일 출력 형식 변경
        val weekStringBuilder = StringBuilder()

        if (result[position].day[0] == "1") weekStringBuilder.append("월 ")
        if (result[position].day[1] == "1") weekStringBuilder.append("화 ")
        if (result[position].day[2] == "1") weekStringBuilder.append("수 ")
        if (result[position].day[3] == "1") weekStringBuilder.append("목 ")
        if (result[position].day[4] == "1") weekStringBuilder.append("금 ")
        if (result[position].day[5] == "1") weekStringBuilder.append("토 ")
        if (result[position].day[6] == "1") weekStringBuilder.append("일 ")

        holder.alert_day.text = weekStringBuilder.toString().trim()

        if(result[position].iot.toString() == "0") {
            holder.binding.enrollItemLo.setBackgroundResource(R.color.back_green)
        }

        if(result[position].iot.toString() == "0") {
            holder.iot.text = "미등록"
        } else {
            holder.iot.text = "등록"
        }
        holder.binding.enrollRemoveBtn.setOnClickListener {
            mItemClickListener.onItemRemove(result[position])
            result.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }
}