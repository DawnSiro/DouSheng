package com.qxy.dousheng.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qxy.dousheng.R
import com.qxy.dousheng.model.rank.RankItem
import com.qxy.dousheng.util.GlideUtils

/**
 * Adapter 适配器层
 * 持有数据集合对象，将数据同步更新到视图上
 */
class RankAdapter(var rankList: List<RankItem>) :
    RecyclerView.Adapter<RankAdapter.ViewHolder>() {

    /**
     * 视图持有类，其成员变量对应 .xml 中的视图
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemPoster: ImageView = view.findViewById(R.id.imageViewPoster)
        val itemName: TextView = view.findViewById(R.id.textViewName)
        val itemTime: TextView = view.findViewById(R.id.textViewTime)
        val itemHot: TextView = view.findViewById(R.id.textViewHot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rank_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rankList[position]
        val poster = item.poster

        // 更新海报
        GlideUtils.load(holder.itemView, poster, holder.itemPoster)

        val rank = position.plus(1)

        holder.itemName.text = rank.toString() + ". " + item.name
        // 热度量级处理
        if (item.hot > 10000) {
            holder.itemHot.text = ("热度: " + (item.hot / 10000).toString() + "万")
        } else {
            holder.itemHot.text = "热度: " + item.hot.toString()
        }

        holder.itemTime.text = item.time
    }

    override fun getItemCount() = rankList.size

}