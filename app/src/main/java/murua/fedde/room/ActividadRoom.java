package murua.fedde.room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import murua.fedde.R;

public class ActividadRoom extends AppCompatActivity implements DiaAdapter.OnDiaItemClick{

    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private DiaDatabase diaDatabase;
    private List<Dia> dias;
    private DiaAdapter notesAdapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_room);
        initializeVies();
        displayList();
    }

    private void displayList(){
        // initialize database instance
        diaDatabase = DiaDatabase.getInstance(ActividadRoom.this);
        // fetch list of dias in background thread
        new ActividadRoom.RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<Dia>> {

        private WeakReference<ActividadRoom> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(ActividadRoom context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Dia> doInBackground(Void... voids) {
            if (activityReference.get()!=null) {
                return activityReference.get().diaDatabase.getDiaDao().getAll();
            }else
                return null;
        }

        @Override
        protected void onPostExecute(List<Dia> dias) {
            if (dias!=null && dias.size()>0 ){
                activityReference.get().dias = dias;

                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);

                // create and set the adapter on RecyclerView instance to display list
                activityReference.get().notesAdapter = new DiaAdapter(dias,activityReference.get());
                activityReference.get().recyclerView.setAdapter(activityReference.get().notesAdapter);
            }
        }

    }

    private void initializeVies(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewMsg =  (TextView) findViewById(R.id.tv__empty);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(listener);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActividadRoom.this));
        dias = new ArrayList<>();
        notesAdapter = new DiaAdapter(dias,ActividadRoom.this);
        recyclerView.setAdapter(notesAdapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(ActividadRoom.this,AgregarDiaVaso.class),100);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode > 0 ){
            if( resultCode == 1){
                dias.add((Dia) data.getSerializableExtra("dia"));
            }else if( resultCode == 2){
                try{
                    dias.set(pos,(Dia) data.getSerializableExtra("dia"));
                } catch (Exception e){
                    Log.e("****","Atrapo excep");
                }

            }
            listVisibility();
        }
    }

    @Override
    public void OnDiaItemClick(final int pos) {
        new AlertDialog.Builder(ActividadRoom.this)
                .setTitle("Seleccionar Opciones")
                .setItems(new String[]{"Borrar", "Actualizar"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                diaDatabase.getDiaDao().delete(dias.get(pos));
                                dias.remove(pos);
                                listVisibility();
                                break;
                            case 1:
                                ActividadRoom.this.pos = pos;
                                startActivityForResult(
                                        new Intent(ActividadRoom.this,
                                                AgregarDiaVaso.class).putExtra("dia",dias.get(pos)),
                                        100);

                                break;
                        }
                    }
                }).show();

    }

    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (dias.size() == 0){ // no item to display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibility);
        notesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        diaDatabase.cleanUp();
        super.onDestroy();
    }

}
