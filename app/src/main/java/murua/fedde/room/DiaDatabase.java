package murua.fedde.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = { Dia.class }, version = 1)
public abstract class DiaDatabase extends RoomDatabase {

    public abstract DiaDao getDiaDao();

    private static DiaDatabase diaDB;

    public static DiaDatabase getInstance(Context context) {
        if (null == diaDB) {
            diaDB = buildDatabaseInstance(context);
        }
        return diaDB;
    }

    private static DiaDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                DiaDatabase.class,
                "ejemplo_room.db")
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        diaDB = null;
    }
}
