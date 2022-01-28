package com.imeofon.roomdatabase.repository

import androidx.lifecycle.LiveData
import com.imeofon.roomdatabase.data.UserDao
import com.imeofon.roomdatabase.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUse(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

}