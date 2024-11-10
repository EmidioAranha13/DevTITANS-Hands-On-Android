package com.example.plaintext.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.plaintext.data.model.Password
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PasswordDao : BaseDao<Password> {
    // Busca todas as senhas e retorna como um Flow
    @Transaction
    @Query("SELECT * FROM passwords")
    abstract fun getAllPasswords(): Flow<List<Password>>

    // Busca uma senha específica pelo ID
    @Transaction
    @Query("SELECT * FROM passwords WHERE id = :id")
    abstract fun getPasswordById(id: Int): Flow<Password?>

    // Busca senhas pelo campo 'name' com correspondência exata
    @Transaction
    @Query("SELECT * FROM passwords WHERE name = :name")
    abstract fun getPasswordsByName(name: String): Flow<List<Password>>
}