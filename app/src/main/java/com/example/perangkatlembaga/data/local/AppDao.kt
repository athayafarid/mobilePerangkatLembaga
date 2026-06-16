package com.example.perangkatlembaga.data.local

import androidx.room.*

@Dao
interface RTDao {
    @Query("SELECT * FROM table_rt")
    suspend fun getAllRT(): List<RTEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRT(rt: RTEntity)

    @Delete
    suspend fun deleteRT(rt: RTEntity)
}

@Dao
interface RWDao {
    @Query("SELECT * FROM table_rw")
    suspend fun getAllRW(): List<RWEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRW(rw: RWEntity)

    @Delete
    suspend fun deleteRW(rw: RWEntity)
}

@Dao
interface AnggotaDao {
    @Query("SELECT * FROM table_anggota")
    suspend fun getAllAnggota(): List<AnggotaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnggota(anggota: AnggotaEntity)

    @Delete
    suspend fun deleteAnggota(anggota: AnggotaEntity)
}