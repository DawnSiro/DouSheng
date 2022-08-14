package com.qxy.dousheng.dao

import androidx.room.*
import com.qxy.dousheng.model.UserInfo


@Dao
interface UserDao {

    @Insert()
    fun addUserInfo(vararg userInfo: UserInfo)

    @Delete()
    fun deleteUserInfo(vararg userInfo: UserInfo)

    @Update()
    fun updateUserInfo(vararg userInfo: UserInfo)

    @Query("select * from user_info")
    fun getUserInfo() : List<UserInfo>

}