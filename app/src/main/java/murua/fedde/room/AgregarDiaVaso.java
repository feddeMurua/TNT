package murua.fedde.room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.Date;

import murua.fedde.R;
import murua.fedde.prueba.MainActivity;

public class AgregarDiaVaso extends AppCompatActivity {

    private DiaDatabase diaDatabase;
    private Dia dia_actual;
    private TextView cant_vasos;
    private String vaso_dia_actual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_dia);

        diaDatabase = DiaDatabase.getInstance(AgregarDiaVaso.this);
        Button button = findViewById(R.id.button_agregar);
        Button button_restar = findViewById(R.id.button_restar);

        cant_vasos = (TextView) findViewById(R.id.txt_cant_vasos);

        if ( ( dia_actual =(Dia)getIntent().getSerializableExtra("dia"))!=null ){
            vaso_dia_actual = diaDatabase.getDiaDao().cantidad(dia_actual.getTitulo());
            cant_vasos.setText(vaso_dia_actual.toString());
        } else {
            dia_actual = diaDatabase.getDiaDao().getDiaActual(new Date(System.currentTimeMillis()));
            if (dia_actual != null){
                vaso_dia_actual = diaDatabase.getDiaDao().cantidad(new Date(System.currentTimeMillis()));
            }else {
                dia_actual = new Dia(0);
                new InsertTask(AgregarDiaVaso.this,dia_actual).execute();
            }
            cant_vasos.setText(dia_actual.getVasos().toString());
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cant_vasos = (TextView) findViewById(R.id.txt_cant_vasos);

                dia_actual.setVasos(dia_actual.getVasos()+1);
                if (dia_actual != null) {
                    diaDatabase.getDiaDao().update(dia_actual);
                    setResult(dia_actual,2);
                }
                 cant_vasos.setText(dia_actual.getVasos().toString());
            }
        });

        button_restar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cant_vasos = (TextView) findViewById(R.id.txt_cant_vasos);

                if (dia_actual.getVasos()==0){
                    dia_actual.setVasos(0);
                } else
                    dia_actual.setVasos(dia_actual.getVasos()-1);

                if (dia_actual != null) {
                    diaDatabase.getDiaDao().update(dia_actual);
                    setResult(dia_actual,2);
                }
                cant_vasos.setText(dia_actual.getVasos().toString());
            }
        });

    } //Fin onCreate

    private void setResult(Dia dia_actual, int flag){
        setResult(flag,new Intent().putExtra("dia", dia_actual));
        if (flag != 2)
            finish();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AgregarDiaVaso> activityReference;
        private Dia dia;

        // only retain a weak reference to the activity
        InsertTask(AgregarDiaVaso context, Dia dia) {
            activityReference = new WeakReference<>(context);
            this.dia = dia;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().diaDatabase.getDiaDao().insert(dia);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(dia,1);
                activityReference.get().finish();
            }
        }

    }

    @Override
    public void onBackPressed() {
        //Cuando se apreta el volver por defecto del telefono
        super.onBackPressed();
        Intent mintent = new Intent(AgregarDiaVaso.this, MainActivity.class);
        startActivity(mintent);
    }

}


