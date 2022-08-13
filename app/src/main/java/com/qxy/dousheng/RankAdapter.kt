package com.qxy.dousheng

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RankAdapter(var rankList: List<MovieItem>) :
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
        holder.itemName.text = item.name
        holder.itemRank.text = position.toString()
        holder.itemHot.text = item.name
        holder.itemTime.text = item.time
    }

    override fun getItemCount() = rankList.size

}