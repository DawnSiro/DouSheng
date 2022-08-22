package com.qxy.dousheng.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.qxy.dousheng.R
import com.qxy.dousheng.model.friend.FriendItem

/**
 * Adapter 适配器层
 * 持有数据集合对象，将数据同步更新到视图上
 */
class FriendAdapter(var friendList: List<FriendItem>) :
    RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.findViewById(R.id.imageViewAvatar)
        val userName: TextView = view.findViewById(R.id.textViewUserName)
        val location: TextView = view.findViewById(R.id.textViewLocation)
        val gender: TextView = view.findViewById(R.id.textViewUserGender)
        val follow: Button = view.findViewById(R.id.buttonFollow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friendItem = friendList[position]

        // 设置图片
        val options: RequestOptions = RequestOptions().transform(CircleCrop())
        // 加载图片
        Glide.with(holder.itemView)
            .load(friendItem.avatar)
            .apply(options)
            .into(holder.avatar)

        holder.userName.text = friendItem.name
        holder.location.text = "${friendItem.country} ${friendItem.province} ${friendItem.city}"
        holder.gender.text = when (friendItem.gender) {
            1 -> holder.itemView.resources.getString(R.string.male)
            2 -> holder.itemView.resources.getString(R.string.female)
            else -> ""
        }

        holder.follow.text =
            when (friendItem.isFollow) {
                1 -> holder.itemView.resources.getString(R.string.is_follow)
                2 -> holder.itemView.resources.getString(R.string.follow)
                3 -> holder.itemView.resources.getString(R.string.mutual_concern)
                else -> "non"
            }
    }

    override fun getItemCount(): Int = friendList.size

}