package com.qxy.dousheng.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qxy.dousheng.R
import com.qxy.dousheng.databinding.ItemUserBinding
import com.qxy.dousheng.model.FollowItem
import com.qxy.dousheng.util.GlideUtils

class FollowAdapter(val context: Context, var followList: List<FollowItem>) : RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {
    // 内部类
    class FollowViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    // 使用 ViewBinding， 一个 Binding 对应绑定一个 layout 里的 .xml 布局
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding: ItemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return FollowViewHolder(binding)
    }

    // 绑定数据，注意 Position 从 0 开始
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        val binding = holder.binding

        val item = followList[position]

        // 更新数据
        // 头像
        GlideUtils.loadCircle(holder.itemView, item.avatar, binding.imageViewAvatar)
        binding.textViewUserName.text = item.name

        binding.textViewUserGender.text = when(item.gender){
            1 -> "男"
            2 -> "女"
            else -> ""
        }
        binding.textViewLocation.text = "${item.country} ${item.province} ${item.city}"
        binding.buttonFollow.text = when(item.isFollow){
            true -> "已关注"
            false -> "关注"
        }

    }

    override fun getItemCount(): Int {
        return followList.size
    }
}