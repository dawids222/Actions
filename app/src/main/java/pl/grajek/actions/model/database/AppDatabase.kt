package pl.grajek.actions.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import pl.grajek.actions.model.converter.DateConverter
import pl.grajek.actions.model.dao.ActionDao
import pl.grajek.actions.model.dao.CategoryDao
import pl.grajek.actions.model.entity.Action
import pl.grajek.actions.model.entity.Category

@Database(entities = [Action::class, Category::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun actionDao(): ActionDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "app.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}