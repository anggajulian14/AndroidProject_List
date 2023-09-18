package com.example.projectlist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectlist.database.DAO.MhsDao
import com.example.projectlist.database.entity.Mahasiswa

@Database(entities = [Mahasiswa::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mhsdao() : MhsDao

    companion object{
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {
            if(instance==null){
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "app-database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return instance!!
        }
    }

}