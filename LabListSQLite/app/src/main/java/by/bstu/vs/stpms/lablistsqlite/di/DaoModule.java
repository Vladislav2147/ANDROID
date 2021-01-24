package by.bstu.vs.stpms.lablistsqlite.di;

import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import by.bstu.vs.stpms.lablistsqlite.model.dao.LabDao;
import by.bstu.vs.stpms.lablistsqlite.model.dao.SubjectDao;
import by.bstu.vs.stpms.lablistsqlite.model.dao.TermDao;
import by.bstu.vs.stpms.lablistsqlite.model.dao.impl.LabDaoImpl;
import by.bstu.vs.stpms.lablistsqlite.model.dao.impl.SubjectDaoImpl;
import by.bstu.vs.stpms.lablistsqlite.model.dao.impl.TermDaoImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class DaoModule {
    @Singleton
    @Provides
    public TermDao provideTermDao(SQLiteDatabase sqliteDatabase) {
        return new TermDaoImpl(sqliteDatabase);
    }

    @Singleton
    @Provides
    public SubjectDao provideSubjectDao(SQLiteDatabase sqliteDatabase) {
        return new SubjectDaoImpl(sqliteDatabase);
    }

    @Singleton
    @Provides
    public LabDao provideLabDao(SQLiteDatabase sqliteDatabase) {
        return new LabDaoImpl(sqliteDatabase);
    }
}
