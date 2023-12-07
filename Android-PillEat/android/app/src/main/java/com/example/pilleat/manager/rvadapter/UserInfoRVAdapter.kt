package com.example.pilleat.manager.rvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pilleat.databinding.ItemUserinfoBinding
import com.example.pilleat.manager.response.Users

class UserInfoRVAdapter(val context: Context, val result: Users): RecyclerView.Adapter<UserInfoRVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemUserinfoBinding) : RecyclerView.ViewHolder(binding.root) {
        val userName: TextView = binding.userInfoNameTv
        val time: TextView = binding.userInfoTimeTv
        val birth: TextView = binding.userInfoBirthTv
        val mode: TextView = binding.userInfoModeTv
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemUserinfoBinding = ItemUserinfoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = result.users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text = result.users[position].name
        holder.time.text = result.users[position].time
        holder.birth.text = result.users[position].birth
        holder.mode.text = result.users[position].mode
    }
}