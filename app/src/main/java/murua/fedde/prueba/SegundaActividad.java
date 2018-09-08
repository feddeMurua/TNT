package murua.fedde.prueba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class SegundaActividad extends AppCompatActivity {

    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segunda_actividad);

        // Referencia al recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Datos del recyclerView
        ItemData itemsData[] = {new ItemData("Peugeot", R.drawable.peugeot),
                                new ItemData("Alfa Romeo", R.drawable.alfa_romeo),
                                new ItemData("Fiat", R.drawable.fiat),
                                new ItemData("Chevrolet", R.drawable.chevrolet),
                                new ItemData("Ford", R.drawable.ford),
                                new ItemData("Honda", R.drawable.honda),
                                new ItemData("Mercedes", R.drawable.mercedes),
                                new ItemData("Renault", R.drawable.renault),
                                new ItemData("Toyota", R.drawable.toyota),
                                new ItemData("Volkswagen", R.drawable.volkswagen),
        };

        // LayoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Se crea el Adaptador
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(itemsData);
        // Set Adaptador
        recyclerView.setAdapter(mAdapter);
        // Set item animator a DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
