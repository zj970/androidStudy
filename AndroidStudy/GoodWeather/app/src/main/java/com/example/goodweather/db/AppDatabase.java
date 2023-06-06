package com.example.goodweather.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.goodweather.bean.MyCity;
import com.example.goodweather.bean.Province;
import com.example.goodweather.db.dao.MyCityDao;
import com.example.goodweather.db.dao.ProvinceDao;

/**
 * @auther zj970
 * @create 2023-06-03 上午12:29
 */
@Database(entities = {Province.class, MyCity.class},version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "GoodWeatherNew";
    private static volatile AppDatabase mInstance;

    public abstract ProvinceDao provinceDao();
    public abstract MyCityDao myCityDao();

    /**
     * 单例模式
     */
    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).addMigrations(MIGRATION_1_2).build();
                }
            }
        }
        return mInstance;
    }

    /**
     * 版本升级迁移到2 新增我的城市表
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `myCity` " +
                    "(cityName TEXT NOT NULL, " +
                    "PRIMARY KEY(`cityName`))");
        }
    };

}
