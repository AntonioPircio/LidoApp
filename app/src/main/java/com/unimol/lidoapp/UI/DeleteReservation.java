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

public class DeleteReservation extends AppCompatActivity {
    private EditText mailBox;
    private Button buttonDelete;
    private String deleteUrl = "deletereservation/dodeletereservation?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletereservation);

        this.mailBox = (EditText) findViewById(R.id.mailBox);
        this.buttonDelete = (Button) findViewById(R.id.deleteReservationButton);

        if (!Preferences.getMailFromPreferences(DeleteReservation.this).equals(Constants.MAILMANAGER)) {
            mailBox.setVisibility(View.INVISIBLE);

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringRequest stringRequest = new StringRequest(Request.Method.DELETE, completeURL(Preferences.getMailFromPreferences(DeleteReservation.this)), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("true")) {
                                Toast.makeText(DeleteReservation.this, "PRENOTAZIONE ELIMINATA CON SUCCESSO", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(DeleteReservation.this, Home.class));
                            } else {
                                Toast.makeText(DeleteReservation.this, "NON HAI ANCORA EFFETTUATO UNA PRENOTAZIONE", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(DeleteReservation.this, "Errore di connessione -> " + volleyError, Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(DeleteReservation.this);
                    requestQueue.add(stringRequest);
                }
            });
        } else {

            this.mailBox.setVisibility(View.VISIBLE);

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringRequest stringRequest = new StringRequest(Request.Method.DELETE, completeURL(mailBox.getText().toString().trim()), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("true")) {
                                Toast.makeText(DeleteReservation.this, "PRENOTAZIONE ELIMINATA CON SUCCESSO", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(DeleteReservation.this, "NON HAI ANCORA EFFETTUATO UNA PRENOTAZIONE", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(DeleteReservation.this, "Errore di connessione -> " + volleyError, Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(DeleteReservation.this);
                    requestQueue.add(stringRequest);
                }
            });
        }
    }

    private String completeURL(String mail) {
        return Constants.URLREST.concat(this.deleteUrl).concat("mail=" + mail);
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
                startActivity(new Intent(DeleteReservation.this, Login.class));

                return true;

            case R.id.prenota:

                startActivity(new Intent(DeleteReservation.this, Reserve.class));

                return true;

            case R.id.home:

                startActivity(new Intent(DeleteReservation.this, Home.class));

                return true;

            case R.id.userinfo:

                startActivity(new Intent(DeleteReservation.this, UserInfo.class));

                return true;

            case R.id.deleteuser:

                startActivity(new Intent(DeleteReservation.this, DeleteUser.class));

                return true;

            case R.id.deletereservation:

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }
}
