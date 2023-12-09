package com.example.pilleat.manager.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pilleat.databinding.ItemUserinfoBinding
import com.example.pilleat.manager.response.UserInfo

class UserInfoRVAdapter(): RecyclerView.Adapter<UserInfoRVAdapter.ViewHolder>() {
    private val userInfo = ArrayList<UserInfo>()
    inner class ViewHolder(val binding: ItemUserinfoBinding) : RecyclerView.ViewHolder(binding.root) {
        val userName: TextView = binding.userInfoNameTv
        val time: TextView = binding.userInfoTimeTv
        val birth: TextView = binding.userInfoBirthTv
        val mode: TextView = binding.userInfoModeTv
    }

    fun setData(userInfo: ArrayList<UserInfo>) {
        this.userInfo.addAll(userInfo)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemUserinfoBinding = ItemUserinfoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = userInfo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text = userInfo[position].name
        holder.time.text = userInfo[position].time
        holder.birth.text = userInfo[position].birth
        holder.mode.text = userInfo[position].mode
    }
}