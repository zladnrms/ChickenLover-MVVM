package defy.tech.chickenlover.model.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserLikeBrand::class, UserLikeType::class], version = 1)
abstract class UserLikeDatabase: RoomDatabase() {

    abstract fun userLikeDao(): UserLikeDao

    companion object {
        private var INSTANCE: UserLikeDatabase? = null

        fun getInstance(context: Context): UserLikeDatabase? {
            if (INSTANCE == null) {
                synchronized(UserLikeDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                         UserLikeDatabase::class.java, "user_like.db")
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
