package com.shaza.billcalculator.repo

import com.shaza.billcalculator.model.User
import com.shaza.billcalculator.room.UserDao
import io.reactivex.Observable

class UserRepo(private val userDao: UserDao) {
    companion object {
        @Volatile
        private var instanceUserRepo: UserRepo? = null

        fun getRepo(userDao: UserDao): UserRepo {
            return instanceUserRepo ?: synchronized(this) {
                val instance = UserRepo(userDao)
                instanceUserRepo = instance
                instance
            }
        }
    }

    fun getAllUsersForThisBill(billId: Int): Observable<List<User>> {
        return userDao.selectUses(billId).toObservable()
    }

    fun insertNewBill(user: User): Observable<Long> {
        return userDao.addUser(user).toObservable()
    }

}