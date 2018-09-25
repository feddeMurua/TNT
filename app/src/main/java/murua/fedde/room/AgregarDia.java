package murua.fedde.room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import murua.fedde.R;

public class AgregarDia extends AppCompatActivity {

    private EditText et_vaso;
    private DiaDatabase diaDatabase;
    private Dia dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dia);
        et_vaso = findViewById(R.id.et_vaso);
        diaDatabase = DiaDatabase.getInstance(AgregarDia.this);
        Button button = findViewById(R.id.but_save);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fetch data and create dia object
                dia = new Dia(5);

                // create worker thread to insert data into database
                new InsertTask(AgregarDia.this,dia).execute();
            }
        });

    }

    private void setResult(Dia dia, int flag){
        setResult(flag,new Intent().putExtra("dia", dia));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AgregarDia> activityReference;
        private Dia dia;

        // only retain a weak reference to the activity
        InsertTask(AgregarDia context, Dia dia) {
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
}
