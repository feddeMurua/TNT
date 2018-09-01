package murua.fedde.prueba;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import static murua.fedde.prueba.NotificationUtils.ANDROID_CHANNEL_ID;

public class MainActivity extends Activity {


    private NotificationUtils mNotificationUtils;

    private ImageView imageView ;
    private Intent intent ;
    public  static final int RequestPermissionCode  = 1 ;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* TOAST */

        View toast_button = findViewById(R.id.toast_button);

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

        /* NOTIFICACIONES */

        mNotificationUtils = new NotificationUtils(this);
        /*
        final EditText editTextTitleAndroid = (EditText) findViewById(R.id.et_android_title);
        final EditText editTextAuthorAndroid = (EditText) findViewById(R.id.et_android_author);
        */
        View notification_button = (Button)findViewById(R.id.notification_button);

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

        /* CLEAN */
       final View clean_button = (Button)findViewById(R.id.clean_button);

        clean_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    imageView = findViewById(R.id.imageView);
                    imageView .setImageResource(android.R.color.transparent);
                    clean_button.setVisibility(View.GONE);
                } catch (Exception e){
                    //No se cargo la imagen todavía
                }

            }
        });

        /* CAMARA */

        View camera_button = (Button)findViewById(R.id.camera_button);
        imageView = (ImageView)findViewById(R.id.imageView);

        EnableRuntimePermission();

        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, 7);

                clean_button.setVisibility(view.VISIBLE);
            }
        });




    } // FIN método OnCreate

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 7 && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(bitmap);
        }
    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.CAMERA))
        {

            Toast.makeText(MainActivity.this,"Permiso de Cámara, permite acceder a la app de la cámara", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this, "Permiso Concedido, La aplicación puede acceder a la cámara.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(MainActivity.this, "Permiso Cancelado, La aplicación NO puede acceder a la cámara.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

} // FIN de la clase

// TODO: 1/9/2018: Desde la app un botón que abra la cámara , cuando sacas la foto volves a la app automáticamente y te muestra la foto en un imageview
