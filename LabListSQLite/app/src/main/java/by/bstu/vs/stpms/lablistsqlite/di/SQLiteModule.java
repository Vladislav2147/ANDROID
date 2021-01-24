package by.bstu.vs.stpms.lablistsqlite.di;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import by.bstu.vs.stpms.lablistsqlite.model.database.DatabaseOpenHelper;
import dagger.Module;
import dagger.Provides;

@Module
public class SQLiteModule {
    @Singleton
    @Provides
    public SQLiteDatabase provideSQLiteDatabase(Context context) {
        return new DatabaseOpenHelper(context).getReadableDatabase();
    }
}
