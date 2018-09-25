package murua.fedde.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DiaDao {

        @Query("SELECT * FROM dia ")
        List<Dia> getAll();

        @Insert
        void insert(Dia dia);

        @Update
        void update(Dia dia);

        @Delete
        void delete(Dia dia);

        @Delete
        void delete(Dia... dia);      // Note... is varargs, here note is an array

}


