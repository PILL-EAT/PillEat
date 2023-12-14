package com.example.pilleat.taker.rvadapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pilleat.R
import com.example.pilleat.WebSocketManager
import com.example.pilleat.databinding.ItemTakingynBinding
import com.example.pilleat.taker.response.EnrollRecordResponse
import com.example.pilleat.taker.response.PillList
import com.example.pilleat.taker.response.RecordResult
import org.json.JSONObject

class TakingYnRVAdapter(val result: EnrollRecordResponse): RecyclerView.Adapter<TakingYnRVAdapter.ViewHolder>() {

    fun setData(data: ArrayList<RecordResult>) {
        //this.result.result.list.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTakingynBinding, val context: Context): RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.takingynNameTv
        val time: TextView = binding.takingynTime
        val button: Button = binding.takingynNoBtn
    }

    interface MyItemClickListener {
        fun onItemClick(result: RecordResult)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TakingYnRVAdapter.ViewHolder {
        val binding: ItemTakingynBinding = ItemTakingynBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding, viewGroup.context)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: TakingYnRVAdapter.ViewHolder, position: Int) {
        holder.name.text = result.result.list[position].name
        holder.time.text = result.result.list[position].time
        if(result.result.list[position].iot == 1) {
            holder.binding.takingynDataLo.setBackgroundResource(R.drawable.sub_edittext)
            holder.button.isEnabled = false
        }

        if(result.result.list[position].finishYN == 1) {
            holder.button.visibility = View.GONE
            holder.binding.takingynYesBtn.visibility = View.VISIBLE
        } else {
            holder.button.visibility = View.VISIBLE
            holder.binding.takingynYesBtn.visibility = View.GONE
        }
        holder.button.setOnClickListener {
            mItemClickListener.onItemClick(result.result.list[position])
            // 여기서 서버에 데이터를 웹소켓으로 전송
            sendToServer(holder.context, result.result.list[position].userId, result.result.list[position].drugId) // id 또는 필요한 데이터를 전송할 수 있도록 수정

            Log.d("socket", result.result.list[position].drugId.toString())

            holder.button.visibility = View.GONE
            holder.binding.takingynYesBtn.visibility = View.VISIBLE
            result.result.list[position].finishYN = 1
            notifyItemRangeChanged(position, result.result.list.size)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return result.result.list.size
    }

    // 서버에 데이터를 웹소켓으로 전송하는 메서드
    private fun sendToServer(context: Context, userId: Int, drugId: Int) {
        val webSocket = WebSocketManager.getInstance(context).getWebSocket()
        val jsonData = createJsonData(userId, drugId)
        webSocket.send(jsonData.toString())
    }

    // JSON 데이터 생성 메서드
    private fun createJsonData(userId: Int, drugId: Int): JSONObject {
        val jsonData = JSONObject()
        jsonData.put("type", "finish") // 적절한 type을 설정
        jsonData.put("clientId", userId) // 클릭된 아이템의 id 또는 필요한 데이터를 전송
        jsonData.put("drugId", drugId)
        return jsonData
    }
}