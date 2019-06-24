package defy.tech.chickenlover.model.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VersionInfo::class], version = 1)
abstract class VersionInfoDatabase: RoomDatabase() {

    abstract fun versionInfoDao(): VersionInfoDao

    companion object {
        private var INSTANCE: VersionInfoDatabase? = null

        fun getInstance(context: Context): VersionInfoDatabase? {
            if (INSTANCE == null) {
                synchronized(VersionInfoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        VersionInfoDatabase::class.java, "version_info.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}
