package murua.fedde.sugar_orm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import murua.fedde.R;

public class AgregarDesarrollador extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_desarrollador);
        SugarContext.init(this);

        // Crea la Tabla si no Existe
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());

        Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText_ap = (EditText) findViewById(R.id.editText_apellido);
                String editText_apellido = editText_ap.getText().toString();

                EditText editText_nmb = (EditText) findViewById(R.id.editText_nombre);
                String editText_nombre = editText_nmb.getText().toString();

                EditText editText_leng = (EditText) findViewById(R.id.editText_lenguaje);
                String editText_lenguaje = editText_leng.getText().toString();

                Desarrollador des = new Desarrollador(editText_apellido, editText_nombre, editText_lenguaje);
                des.save();
            }
        });


    } // Fin OnCreate
}