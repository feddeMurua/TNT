package murua.fedde.sugar_orm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import murua.fedde.R;

public class SugarORM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_sugar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(listener);
        /*
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActividadRoom.this));
        dias = new ArrayList<>();
        notesAdapter = new DiaAdapter(dias,ActividadRoom.this);
        recyclerView.setAdapter(notesAdapter);
        */
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(SugarORM.this,AgregarDesarrollador.class),100);
        }
    };


}
