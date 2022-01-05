package com.vaikunj.example.db

class UserRoomRepository(private val dao: UserRoomDAO) {

    val subscribers = dao.getAllSubscribers()

    suspend fun insert(userRoomModel: UserRoomModel): Long {
        return dao.insertSubscriber(userRoomModel)
    }

    suspend fun update(userRoomModel: UserRoomModel): Int {
        return dao.updateSubscriber(userRoomModel)
    }

    suspend fun delete(userRoomModel: UserRoomModel): Int {
        return dao.deleteSubscriber(userRoomModel)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}