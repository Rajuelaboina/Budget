package com.task.task

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBank(bankNames: BankNames)

     @Query("SELECT EXISTS(SELECT * FROM bank WHERE BankName = :BankName )")
     fun isBankExists(BankName: String): Boolean

    @Query("SELECT * FROM bank")
    fun getAllBankNames(): List<BankNames>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserData(userData: UserData)

    @Query("SELECT * FROM userData")
    fun getAllUserData(): List<UserData>

    @Query("SELECT SUM(creditAmount) FROM userData")
    fun getSum(): Int

    @Query("SELECT SUM(depositAmount) FROM userData")
    fun getSum2(): Int

    @Update
    fun updateUserData(userData: UserData)

    @Query("DELETE FROM userData WHERE id = :id")
    fun deleteUser(id: String)
}