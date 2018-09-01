package murua.fedde.prueba;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button button;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.mainbutton);

        button.setOnClickListener(new OnClickListener() {
            /*
            @Override
            public void onClick(View arg0) {
                //muestra solo un mensaje
                Toast.makeText(getApplicationContext(),"Mensaje Toast", Toast.LENGTH_LONG).show();
            }
            */

            @Override
            public void onClick(View view) {
                //muestra mensaje con una imagen
                // get your toast.xml layout
                LayoutInflater inflater = getLayoutInflater();

                View layout = inflater.inflate(R.layout.toast,(ViewGroup) findViewById(R.id.toast_layout_id));

                // set a message
                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText("Mensaje Toast con imagen");

                // Toast configuration
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }
}
