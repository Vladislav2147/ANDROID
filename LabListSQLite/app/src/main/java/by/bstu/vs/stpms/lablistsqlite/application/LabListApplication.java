package by.bstu.vs.stpms.lablistsqlite.application;

import android.app.Application;

import by.bstu.vs.stpms.lablistsqlite.di.AppComponent;
import by.bstu.vs.stpms.lablistsqlite.di.AppModule;
import by.bstu.vs.stpms.lablistsqlite.di.DaggerAppComponent;


public class LabListApplication extends Application {
    public AppComponent appComponent = DaggerAppComponent
            .builder()
            .appModule(new AppModule(this))
            .build();
}
