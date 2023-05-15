package com.unimol.lidoapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {
    private EditText mailBox;
    private EditText passwordBox;
    private Button loginButton;
    private TextView registerLink;
    private TextView changePasswordLink;
    private String loginUrl = "login/dologin?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.mailBox = (EditText) findViewById(R.id.mailBox);
        this.passwordBox = (EditText) findViewById(R.id.passwordBox);
        this.loginButton = (Button) findViewById(R.id.loginButton);
        this.registerLink = (TextView) findViewById(R.id.registerLink);
        this.changePasswordLink = (TextView) findViewById(R.id.newPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL(mailBox.getText().toString().trim(), passwordBox.getText().toString().trim()), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("true")) {
                                Preferences.savePreferences(Login.this, mailBox.getText().toString().trim(), passwordBox.getText().toString().trim());
                                Toast.makeText(Login.this, "LOGIN AVVENUTO CON SUCCESSO", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this, Home.class));
                            } else {
                                Toast.makeText(Login.this, "I CAMPI INSERITI NON SONO CORRETTI", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(Login.this, "Errore di connessione -> " + volleyError, Toast.LENGTH_LONG).show();

                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                    requestQueue.add(stringRequest);
                }
            }
        });

        this.registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        this.changePasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });
    }


    private String completeURL(String mail, String password) {
        return Constants.URLREST.concat(this.loginUrl).concat("mail=" + mail + "&password=" + password);
    }

    private boolean checkMail() {
        String mail = mailBox.getText().toString().trim();

        if (mail.isEmpty()) {
            mailBox.setError("Il campo non può essere vuoto");
            return false;
        } else {
            mailBox.setError(null);
            return true;
        }
    }


    private boolean checkPassword() {
        String password = passwordBox.getText().toString().trim();

        if (password.isEmpty()) {
            passwordBox.setError("Il campo non può essere vuoto");
            return false;
        } else {
            passwordBox.setError(null);
            return true;
        }
    }

    private boolean checkInput() {
        if (checkMail() && checkPassword()) {
            return true;
        }
        return false;
    }

}
