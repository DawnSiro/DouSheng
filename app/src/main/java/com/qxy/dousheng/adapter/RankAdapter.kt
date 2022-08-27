package com.qxy.dousheng.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemPoster: ImageView = view.findViewById(R.id.imageViewPoster)
        val itemName: TextView = view.findViewById(R.id.textViewName)
        val itemEnglishName: TextView = view.findViewById(R.id.text_view_english_name)
        val itemDirectors: TextView = view.findViewById(R.id.text_view_directors)
        val itemAreas: TextView = view.findViewById(R.id.text_view_areas)
        val itemActors: TextView = view.findViewById(R.id.text_view_actors)
        val itemTags: TextView = view.findViewById(R.id.text_view_tags)
        val itemReleaseDate: TextView = view.findViewById(R.id.text_view_release_date)
        val itemHot: TextView = view.findViewById(R.id.textViewHot)
        val itemDiscussionHot: TextView = view.findViewById(R.id.text_view_discussion_hot)
        val itemTopicHot: TextView = view.findViewById(R.id.text_view_topic_hot)
        val itemSearchHot: TextView = view.findViewById(R.id.text_view_search_hot)
        val itemInfluenceHot: TextView = view.findViewById(R.id.text_view_influence_hot)
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

        // 电影过长的话进行省略处理
        if(item.name.length > 9) {
            holder.itemName.text = "$rank. ${item.name.substring(0, 8)}..."
        } else {
            holder.itemName.text = "$rank. ${item.name}"
        }

        // 电影英文名过长的话进行省略处理
        if(item.englishName.length > 20){
            holder.itemEnglishName.text = "${item.englishName.substring(0, 19)}..."
        } else {
            holder.itemEnglishName.text = item.englishName
        }

        // 导演
        if(item.directors != ""){
            if(item.directors.length > 6) {
                holder.itemDirectors.text = "导演: ${item.directors.substring(0, 5)}..."
            } else {
                holder.itemDirectors.text = "导演: ${item.directors}"
            }
        }else{
            holder.itemDirectors.text = "导演: 无"
        }


        // 上映区域
        if(item.areas != ""){
            if(item.areas.length > 6) {
                holder.itemAreas.text = "上映区域: ${item.areas.substring(0, 5)}..."
            } else {
                holder.itemAreas.text = "上映区域: ${item.areas}"
            }
        } else {
            holder.itemAreas.text = "上映区域: 暂无"
        }


        // 演员
        if(item.actors != ""){
            if(item.actors.length > 12) {
                holder.itemActors.text = "演员: ${item.actors.substring(0, 11)}..."
            } else {
                holder.itemActors.text = "演员: ${item.actors}"
            }
        }else{
            holder.itemActors.text = "演员: 暂无"
        }


        // 标签
        if(item.tags != ""){
            holder.itemTags.text = "标签: ${item.tags}"
        }else{
            holder.itemTags.text = "标签: 暂无"
        }



        // 热度量级处理
        if(item.hot > 100000000) {
            holder.itemHot.text = "热度: ${(item.hot / 100000000)}亿"
        } else if (item.hot > 10000) {
            holder.itemHot.text = "热度: ${item.hot / 10000}万"
        } else {
            holder.itemHot.text = "热度: ${item.hot}"
        }

        // 讨论热度量级处理
        if(item.discussionHot > 100000000) {
            holder.itemDiscussionHot.text = "讨论热度: ${(item.discussionHot / 100000000)}亿"
        } else if (item.discussionHot > 10000) {
            holder.itemDiscussionHot.text = "讨论热度: ${(item.discussionHot / 10000)}万"
        } else {
            holder.itemDiscussionHot.text = "讨论热度: ${item.hot}"
        }

        // 主题热度量级处理
        if(item.topicHot > 100000000) {
            holder.itemTopicHot.text = "主题热度: ${(item.topicHot / 100000000)}亿"
        } else if (item.topicHot > 10000) {
            holder.itemTopicHot.text = "主题热度: ${(item.topicHot / 10000)}万"
        } else {
            holder.itemTopicHot.text = "主题热度: ${item.topicHot}"
        }

        // 搜索热度量级处理
        if(item.searchHot > 100000000) {
            holder.itemSearchHot.text = "搜索热度: ${(item.searchHot / 100000000)}亿"
        } else if (item.searchHot > 10000) {
            holder.itemSearchHot.text = "搜索热度: ${(item.searchHot / 10000)}万"
        } else {
            holder.itemSearchHot.text = "搜索热度: ${item.searchHot}"
        }

        // 影响热度量级处理
        if(item.influenceHot > 100000000) {
            holder.itemInfluenceHot.text = "影响热度: ${(item.influenceHot / 100000000)}亿"
        } else if (item.influenceHot > 10000) {
            holder.itemInfluenceHot.text = "影响热度: ${(item.influenceHot / 10000)}万"
        } else {
            holder.itemInfluenceHot.text = "影响热度: ${item.influenceHot}"
        }

        holder.itemReleaseDate.text = "上映日期：${item.releaseDate}"
    }

    override fun getItemCount() = rankList.size

}