package com.unimol.lidoapp.UI;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.unimol.lidoapp.R;
import com.unimol.lidoapp.app.Constants;
import com.unimol.lidoapp.app.Preferences;
import org.json.JSONArray;
import org.json.JSONException;

public class UserInfo extends AppCompatActivity {
    private TextView textView;
    private Button infoButton;
    private String userInfoUrl = "userservice/userinfo?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);

        this.textView = (TextView) findViewById(R.id.userDisplay);
        this.infoButton = (Button) findViewById(R.id.userInfoButton);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, completeURL(Preferences.getMailFromPreferences(UserInfo.this)), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.contains("false")) {
                                try {
                                    StringBuilder userInfo = new StringBuilder();
                                    JSONArray responseJSONArray = new JSONArray(response);
                                    for (int i = 0; i < responseJSONArray.length(); i++) {
                                        userInfo.append("\n" +
                                                " Nome: " + responseJSONArray.getJSONObject(i).getString("name") +
                                                "\n Cognome " + responseJSONArray.getJSONObject(i).getString("surname") +
                                                "\n Mail: " + responseJSONArray.optJSONObject(i).getString("mail") +
                                                "\n ID Ombrellone: " + responseJSONArray.getJSONObject(i).getString("ID_affittato") +
                                                "\n Numero Sedie: " + responseJSONArray.getJSONObject(i).getInt("numero_sedie") +
                                                "\n Numero Sdraio: " + responseJSONArray.getJSONObject(i).getInt("numero_sdraio") +
                                                "\n Prezzo: " + responseJSONArray.getJSONObject(i).getInt("prezzo")  + " €"+
                                                "\n\n");
                                    }
                                    textView.setText("INFO USER \n" + userInfo);
                                    textView.setMovementMethod(new ScrollingMovementMethod());
                                } catch (JSONException jsonException) {
                                    Toast.makeText(UserInfo.this, "Errore -> " + jsonException, Toast.LENGTH_LONG).show();
                                }
                            } else {
                                    Toast.makeText(UserInfo.this, "NON HAI ANCORA EFFETTUATO UNA PRENOTAZIONE", Toast.LENGTH_LONG).show();
                                }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(UserInfo.this, "Errore -> " + volleyError, Toast.LENGTH_LONG).show();

                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(UserInfo.this);
                    requestQueue.add(stringRequest);
                }
        });
    }

    private String completeURL(String mail) {
        return Constants.URLREST.concat(this.userInfoUrl).concat("mail=" + mail);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.my_menu, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout:
                Preferences.clearPreferences(this);
                startActivity(new Intent(UserInfo.this, Login.class));

                return true;

            case R.id.prenota:

                startActivity(new Intent(UserInfo.this, Reserve.class));

                return true;

            case R.id.home:

                startActivity(new Intent(UserInfo.this, Home.class));

                return true;

            case R.id.userinfo:

                return true;

            case R.id.deleteuser:

                startActivity(new Intent(UserInfo.this, DeleteUser.class));

                return true;

            case R.id.deletereservation:

                startActivity(new Intent(UserInfo.this, DeleteReservation.class));

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

}
