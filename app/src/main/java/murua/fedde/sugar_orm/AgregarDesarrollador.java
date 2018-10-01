package murua.fedde.sugar_orm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.lang.ref.WeakReference;

import murua.fedde.R;

public class AgregarDesarrollador extends AppCompatActivity {

    private Desarrollador desarrollador_actual;
    EditText editText_ap;
    EditText editText_nmb;
    EditText editText_leng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_desarrollador);
        editText_ap = (EditText) findViewById(R.id.editText_apellido);
        editText_nmb = (EditText) findViewById(R.id.editText_nombre);
        editText_leng = (EditText) findViewById(R.id.editText_lenguaje);
        SugarContext.init(this);

        if ( ( desarrollador_actual =(Desarrollador) getIntent().getSerializableExtra("desarrollador"))!=null ){
            editText_ap.setText(desarrollador_actual.getApellido());
            editText_nmb.setText(desarrollador_actual.getNombre());
            editText_leng.setText(desarrollador_actual.getLenguajeFavorito());
        }

        Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editText_apellido = editText_ap.getText().toString();
                String editText_nombre = editText_nmb.getText().toString();
                String editText_lenguaje = editText_leng.getText().toString();

                Desarrollador dev = new Desarrollador(editText_apellido, editText_nombre, editText_lenguaje);

                if (desarrollador_actual != null){
                    desarrollador_actual = dev;
                    desarrollador_actual.save();
                    setResult(desarrollador_actual,2);
                } else {
                    desarrollador_actual = dev;
                    new InsertTask(AgregarDesarrollador.this,desarrollador_actual).execute();
                }

            }
        });


    } // Fin OnCreate


    private void setResult(Desarrollador des, int flag){
        setResult(flag,new Intent().putExtra("desarrollador", des));
        finish();
    }


    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AgregarDesarrollador> activityReference;
        private Desarrollador desarrollador;

        // only retain a weak reference to the activity
        InsertTask(AgregarDesarrollador context, Desarrollador desarrollador) {
            activityReference = new WeakReference<>(context);
            this.desarrollador = desarrollador;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().desarrollador_actual.save();
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(desarrollador,1);
                activityReference.get().finish();
            }
        }

    }

    @Override
    public void onBackPressed() {
        //Cuando se apreta el volver por defecto del telefono
        super.onBackPressed();
        Intent mintent = new Intent(AgregarDesarrollador.this, SugarORM.class);
        startActivity(mintent);
    }
}