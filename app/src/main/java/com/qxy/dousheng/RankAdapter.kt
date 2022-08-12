package com.qxy.dousheng

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView 的显示
 * @param rankList 一个 List<RankItem> 列表
 */
class RankAdapter(private val rankList: List<RankItem>) :
    RecyclerView.Adapter<RankAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.imageView)
        val itemName: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rank_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rankList[position]
        holder.itemImage.setImageResource(item.imageUrl)
        holder.itemName.text = item.name
    }

    override fun getItemCount() = rankList.size
}