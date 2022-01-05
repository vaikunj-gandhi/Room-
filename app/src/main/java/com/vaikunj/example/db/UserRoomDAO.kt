package com.vaikunj.example.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRoomDAO {

    @Insert
    suspend fun insertSubscriber(userRoomModel: UserRoomModel) : Long

    @Update
    suspend fun updateSubscriber(userRoomModel: UserRoomModel) : Int

    @Delete
    suspend fun deleteSubscriber(userRoomModel: UserRoomModel) : Int

    @Query("DELETE FROM vaikunj_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM vaikunj_table")
    fun getAllSubscribers():Flow<List<UserRoomModel>>
}