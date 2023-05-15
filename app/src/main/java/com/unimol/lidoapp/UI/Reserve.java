package com.unimol.lidoapp.UI;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Reserve extends AppCompatActivity {
    private EditText ID_UmbrellaBox;
    private Button reserveButton;
    private String reserveUrl = "reservation/doreservation?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve);

        this.ID_UmbrellaBox = (EditText) findViewById(R.id.ID_Umbrella);
        this.reserveButton = (Button) findViewById(R.id.reserveButton);

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIDUmbrella()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL(Preferences.getMailFromPreferences(Reserve.this), ID_UmbrellaBox.getText().toString().trim()), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("true")) {
                                Toast.makeText(Reserve.this, "PRENOTAZIONE AVVENUTA CON SUCCESSO", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Reserve.this, Home.class));
                            } else {
                                Toast.makeText(Reserve.this, "OMBRELLONE OCCUPATO O ID NON VALIDO", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(Reserve.this, "Errore di connessione -> " + volleyError, Toast.LENGTH_LONG).show();
                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(Reserve.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private String completeURL(String mail, String ID_Umbrella) {
        return Constants.URLREST.concat(this.reserveUrl).concat("mail=" + mail + "&ID_Umbrella=" + ID_Umbrella);
    }

    private boolean checkIDUmbrella() {
        String idUmbrella = ID_UmbrellaBox.getText().toString().trim();

        if (idUmbrella.isEmpty()) {
            ID_UmbrellaBox.setError("Il campo non può essere vuoto");
            return false;
        }
        return true;
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
                startActivity(new Intent(Reserve.this, Login.class));

                return true;

            case R.id.prenota:

                return true;

            case R.id.home:

                startActivity(new Intent(Reserve.this, Home.class));

                return true;

            case R.id.userinfo:

                startActivity(new Intent(Reserve.this, UserInfo.class));

                return true;

            case R.id.deleteuser:

                startActivity(new Intent(Reserve.this, DeleteUser.class));

                return true;

            case R.id.deletereservation:

                startActivity(new Intent(Reserve.this, DeleteReservation.class));

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

}
