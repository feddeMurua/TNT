package murua.fedde.sugar_orm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import murua.fedde.R;

public class SugarORM extends AppCompatActivity implements  DesarrolladorAdapter.OnDevItemClick{

    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private List<Desarrollador> desarrolladores;
    private DesarrolladorAdapter desarrolladorAdapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_sugar);
        initializeVies();
        displayList();
    }

    private void displayList(){
        // fetch list of desarrolladores in background thread
        new SugarORM.RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<Desarrollador>> {

        private WeakReference<SugarORM> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(SugarORM context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Desarrollador> doInBackground(Void... voids) {
            if (activityReference.get()!=null) {
                return activityReference.get().desarrolladores;
            }else
                return null;
        }

        @Override
        protected void onPostExecute(List<Desarrollador> desarrolladores) {
            if (desarrolladores!=null && desarrolladores.size()>0 ){
                activityReference.get().desarrolladores = desarrolladores;

                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);

                // create and set the adapter on RecyclerView instance to display list
                activityReference.get().desarrolladorAdapter= new DesarrolladorAdapter(desarrolladores,activityReference.get());
                activityReference.get().recyclerView.setAdapter(activityReference.get().desarrolladorAdapter);
            }
        }

    }

    private void initializeVies() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewMsg = (TextView) findViewById(R.id.tv__empty);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(listener);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(SugarORM.this));

        desarrolladores = Desarrollador.listAll(Desarrollador.class);
        desarrolladorAdapter = new DesarrolladorAdapter(desarrolladores, SugarORM.this);
        recyclerView.setAdapter(desarrolladorAdapter);
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(SugarORM.this,AgregarDesarrollador.class),100);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode > 0 ){
            if( resultCode == 1){
                desarrolladores.add((Desarrollador) data.getSerializableExtra("desarrollador"));
            }else if( resultCode == 2){
                desarrolladores.set(pos,(Desarrollador) data.getSerializableExtra("desarrollador"));
            }
            listVisibility();
        }
    }

    @Override
    public void OnDevItemClick(final int pos) {
        new AlertDialog.Builder(SugarORM.this)
                .setTitle("Seleccionar Opciones")
                .setItems(new String[]{"Borrar", "Actualizar"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                desarrolladores.get(pos).delete();
                                desarrolladores.remove(pos);
                                listVisibility();
                                break;
                            case 1:

                                SugarORM.this.pos = pos;

                                startActivityForResult(
                                        new Intent(SugarORM.this,
                                                AgregarDesarrollador.class).putExtra("desarrollador", desarrolladores.get(pos)),
                                        100);


                                break;
                        }
                    }
                }).show();

    }

    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (desarrolladores.size() == 0){ // no item to display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibility);
        desarrolladorAdapter.notifyDataSetChanged();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
