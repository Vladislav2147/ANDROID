package by.bstu.vs.stpms.lablistsqlite.di;

import javax.inject.Singleton;

import by.bstu.vs.stpms.lablistsqlite.ui.lab.LabCreateFragment;
import by.bstu.vs.stpms.lablistsqlite.ui.lab.LabDetailsFragment;
import by.bstu.vs.stpms.lablistsqlite.ui.lab.LabFragment;
import by.bstu.vs.stpms.lablistsqlite.ui.subject.SubjectFragment;
import by.bstu.vs.stpms.lablistsqlite.ui.term.TermFragment;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, SQLiteModule.class, DaoModule.class})
public interface AppComponent {
    void inject(TermFragment fragment);
    void inject(SubjectFragment fragment);
    void inject(LabFragment fragment);
    void inject(LabCreateFragment fragment);
    void inject(LabDetailsFragment fragment);

}
