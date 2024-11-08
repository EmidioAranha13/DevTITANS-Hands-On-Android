package com.example.plaintext.data.dao

import androidx.room.Dao
import com.example.plaintext.data.model.Password

@Dao
abstract class PasswordDao : BaseDao<Password> {
    // Busca todas as senhas e retorna como um Flow
    @Query("SELECT * FROM passwords")
    fun getAllPasswords(): Flow<List<Password>>

    // Busca uma senha específica pelo ID
    @Query("SELECT * FROM passwords WHERE id = :id")
    fun getPasswordById(id: Int): Flow<Password?>

    // Busca senhas pelo campo 'name' com correspondência exata
    @Query("SELECT * FROM passwords WHERE name = :name")
    fun getPasswordsByName(name: String): Flow<List<Password>>
}