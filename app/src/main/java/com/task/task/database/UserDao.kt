package com.task.task.database

import androidx.room.*
import com.task.task.model.BankNames
import com.task.task.model.BankNames2
import com.task.task.model.UserData

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBank(bankNames: BankNames)

     @Query("SELECT EXISTS(SELECT * FROM bank WHERE BankName = :BankName )")
     fun isBankExists(BankName: String): Boolean

    //@Query("SELECT * FROM bank")
    @Query("SELECT BankName FROM bank")
    fun getAllBankNames(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserData(userData: UserData)

    @Query("SELECT * FROM userData")
    fun getAllUserData(): List<UserData>

    @Query("SELECT SUM(creditAmount) FROM userData")
    fun getSum(): Int

    @Query("SELECT SUM(withdrawAmount) FROM userData")
    fun getSum2(): Int

    @Update
    fun updateUserData(userData: UserData)

    @Query("DELETE FROM userData WHERE id = :id")
    fun deleteUser(id: String)

    @Query("SELECT SUM(availableAmount) FROM userData")
    fun getTotalOfSum():Int
    @Query("SELECT SUM(availableAmount) FROM userData WHERE bankname = :bankname")
    fun getAvailableOfSum(bankname: String):Int

    @Query("SELECT SUM(creditAmount) FROM userData WHERE bankname = :bankname")
    fun getAvailableCreditMonth(bankname: String):Int

    @Query("SELECT SUM(withdrawAmount) FROM userData WHERE bankname = :bankname")
    fun getAvailableWithdrawMonth(bankname: String):Int

    @Query("DELETE FROM bank WHERE id = :id")
    fun deleteBank(id: String)

    @Query("SELECT * FROM bank")
    fun getBankNames(): List<BankNames>

}