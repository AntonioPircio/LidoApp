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

public class DeleteUser extends AppCompatActivity {
    private EditText mailBox;
    private Button buttonDelete;
    private String deleteUrl = "delete/dodelete?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deleteuser);

        this.mailBox = (EditText) findViewById(R.id.mailBox);
        this.buttonDelete = (Button) findViewById(R.id.deleteUserButton);

        if (!Preferences.getMailFromPreferences(DeleteUser.this).equals(Constants.MAILMANAGER)) {
            mailBox.setVisibility(View.INVISIBLE);

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringRequest stringRequest = new StringRequest(Request.Method.DELETE, completeURL(Preferences.getMailFromPreferences(DeleteUser.this)), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("true")) {
                                Toast.makeText(DeleteUser.this, "ACCOUNT ELIMINATO CON SUCCESSO", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(DeleteUser.this, Login.class));
                            } else {
                                Toast.makeText(DeleteUser.this, "NON SEI REGISTRATO", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(DeleteUser.this, "Errore di connessione -> " + volleyError, Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(DeleteUser.this);
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
                                Toast.makeText(DeleteUser.this, "ACCOUNT ELIMINATO CON SUCCESSO", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(DeleteUser.this, "NON SEI REGISTRATO", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(DeleteUser.this, "Errore di connessione -> " + volleyError, Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(DeleteUser.this);
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
                startActivity(new Intent(DeleteUser.this, Login.class));

                return true;

            case R.id.prenota:

                startActivity(new Intent(DeleteUser.this, Reserve.class));

                return true;

            case R.id.home:

                startActivity(new Intent(DeleteUser.this, Home.class));

                return true;

            case R.id.userinfo:

                startActivity(new Intent(DeleteUser.this, UserInfo.class));

                return true;

            case R.id.deleteuser:

                return true;

            case R.id.deletereservation:

                startActivity(new Intent(DeleteUser.this, DeleteReservation.class));

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }
}
