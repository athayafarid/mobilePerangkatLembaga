package com.example.perangkatlembaga.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_rt")
data class RTEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomorRT: String,
    val ketuaRT: String
)

@Entity(tableName = "table_rw")
data class RWEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomorRW: String,
    val ketuaRW: String
)

@Entity(tableName = "table_anggota")
data class AnggotaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val jabatan: String
)