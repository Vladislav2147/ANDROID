package by.bstu.vs.stpms.lablistsqlite.ui.settings;

import java.util.List;
import java.util.Optional;

public enum Picture {
    PROGRAMMING(1), PHYSICS(2), CHEMISTRY(3);
    private int id;

    Picture(int id) {
        this.id = id;
    }

    public static Optional<Picture> getById(int id) {
        return List
                .of(Picture.values())
                .stream()
                .filter(picture -> picture.id == id)
                .findFirst();
    }


}
