package com.qxy.dousheng.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qxy.dousheng.R
import com.qxy.dousheng.model.RankItem

class RankAdapter(var rankList: List<RankItem>) :
    RecyclerView.Adapter<RankAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.textViewName)
        val itemRank: TextView = view.findViewById(R.id.textViewRank)
        val itemTime: TextView = view.findViewById(R.id.textViewTime)
        val itemHot: TextView = view.findViewById(R.id.textViewHot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rankList[position]
        val rank = position.plus(1)
        holder.itemName.text = item.name
        holder.itemRank.text = rank.toString()
        holder.itemHot.text = item.hot.toString()
        holder.itemTime.text = item.time
    }

    override fun getItemCount() = rankList.size

}