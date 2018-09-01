package murua.fedde.prueba;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static murua.fedde.prueba.NotificationUtils.ANDROID_CHANNEL_ID;

public class MainActivity extends Activity {

    private Button toast_button;
    private Button notification_button;
    private NotificationUtils mNotificationUtils;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toast_button = findViewById(R.id.toast_button);

        toast_button.setOnClickListener(new OnClickListener() {
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

        mNotificationUtils = new NotificationUtils(this);
        /*
        final EditText editTextTitleAndroid = (EditText) findViewById(R.id.et_android_title);
        final EditText editTextAuthorAndroid = (EditText) findViewById(R.id.et_android_author);
        */
        notification_button = (Button)findViewById(R.id.notification_button);

        notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                String title = editTextTitleAndroid.getText().toString();
                String author = editTextAuthorAndroid.getText().toString();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(author)) {
                    Notification.Builder nb = mNotificationUtils.getAndroidChannelNotification(title, "By " + author);
                    mNotificationUtils.getManager().notify(101, nb.build());
                }
                */
                Notification.Builder nb = mNotificationUtils.getAndroidChannelNotification("Título de la Notificación", "Cuerpo del Mensaje");
                mNotificationUtils.getManager().notify(101, nb.build());
            }
        });

    } // FIN método OnCreate



}
