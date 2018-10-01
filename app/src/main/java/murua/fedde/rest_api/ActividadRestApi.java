package murua.fedde.rest_api;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import murua.fedde.R;

public class ActividadRestApi extends Activity {

    private Context mContext;
    private Activity mActivity;

    private CoordinatorLayout mCLayout;
    private Button button_test_api;
    private TextView mTextView;
    private String url= "http://www.mocky.io/v2/5bb281f4330000520011c93b";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_rest_api);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = ActividadRestApi.this;

        // Get the widget reference from XML layout
        mCLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        button_test_api = (Button) findViewById(R.id.button_test_api);
        mTextView = (TextView) findViewById(R.id.tv);

        button_test_api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Empty the TextView
                mTextView.setText("");

                // Initialize a new RequestQueue instance
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                // Initialize a new JsonObjectRequest instance
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Do something with response
                                //mTextView.setText(response.toString());

                                // Process the JSON
                                try{
                                    // Get the JSON array
                                    JSONArray array = response.getJSONArray("dias");

                                    // Loop through the array elements
                                    for(int i=0;i<array.length();i++){
                                        // Get current json object
                                        JSONObject dia = array.getJSONObject(i);

                                        // Get the current student (json object) data
                                        String titulo = dia.getString("titulo");
                                        String vasos = dia.getString("vasos");

                                        // Display the formatted json data in text view
                                        mTextView.append("DIA: "+titulo +", " + vasos + " vasos.");
                                        mTextView.append("\n\n");
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                // Do something when error occurred
                                Snackbar.make(
                                        mCLayout,
                                        " Se Produjo un Error.",
                                        Snackbar.LENGTH_LONG
                                ).show();
                            }
                        }
                );

                // Add JsonObjectRequest to the RequestQueue
                requestQueue.add(jsonObjectRequest);

            }
        });

    } // Fin OnCreate

}
