package com.imeofon.roomdatabase.data

import androidx.lifecycle.LiveData
import com.imeofon.roomdatabase.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

}