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


public class Register extends AppCompatActivity {
    private EditText nameBox;
    private EditText surnameBox;
    private EditText mailBox;
    private EditText passwordBox;
    private Button registerButton;
    private TextView loginLink;
    private String registerUrl = "register/doregister?";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        this.nameBox = (EditText) findViewById(R.id.nameBox);
        this.surnameBox = (EditText) findViewById(R.id.surnameBox);
        this.mailBox = (EditText) findViewById(R.id.mailBox);
        this.passwordBox = (EditText) findViewById(R.id.passwordBox);
        this.registerButton = (Button) findViewById(R.id.registerButton);
        this.loginLink = (TextView) findViewById(R.id.loginLink);


        this.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL
                            (nameBox.getText().toString().trim(), surnameBox.getText().toString().trim(),
                                    mailBox.getText().toString().trim(), passwordBox.getText().toString().trim()), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("true")) {
                                Preferences.savePreferences(Register.this, mailBox.getText().toString().trim(), passwordBox.getText().toString().trim());
                                Toast.makeText(Register.this, "REGISTRAZIONE AVVENUTA CON SUCCESSO", Toast.LENGTH_LONG).show();
                                registerButton.setPressed(false);
                                startActivity(new Intent(Register.this, Home.class));
                            } else {
                                Toast.makeText(Register.this, "SEI GIA' REGISTRATO", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(Register.this, "Errore di connessione -> " + volleyError, Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                    requestQueue.add(stringRequest);
                }
            }
        });


        this.loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private String completeURL(String name, String surname, String mail, String password) {
        return Constants.URLREST.concat(this.registerUrl).concat("name=" + name + "&surname=" + surname + "&mail=" + mail + "&password=" + password);
    }

    private boolean checkName() {
        String name = nameBox.getText().toString().trim();

        if (name.isEmpty()) {
            nameBox.setError("Il campo non può essere vuoto");
            return false;
        } else if (name.length() > Constants.MAXNAMELENGTH) {
           nameBox.setError("Nome è troppo lungo");
            return false;
        } else {
            nameBox.setError(null);
            return true;
        }
    }

    private boolean checkSurname() {
        String surname = surnameBox.getText().toString().trim();

        if (surname.isEmpty()) {
            nameBox.setError("Il campo non può essere vuoto");
            return false;
        } else if (surname.length() > Constants.MAXSURNAMELENGTH) {
            nameBox.setError("Cognome troppo lungo");
            return false;
        } else {
            nameBox.setError(null);
            return true;
        }
    }

    private boolean checkMail() {
        String mail = mailBox.getText().toString().trim();

        if (mail.isEmpty()) {
            mailBox.setError("Il campo non può essere vuoto");
            return false;
        } else if (!Constants.MAIL_ADDRESS_PATTERN.matcher(mail).matches()) {
            mailBox.setError("Si prega di immettere una mail valida");
            return false;
        } else if (mail.length() > Constants.MAXMAILLENGTH) {
            mailBox.setError("Mail troppo lunga");
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
        } else if (!Constants.PASSWORD_PATTERN.matcher(password).matches()) {
            passwordBox.setError("Password troppo debole");
            return false;
        } else if (password.length() > Constants.MAXPASSWORDLENGTH) {
            passwordBox.setError("Password troppo lunga");
            return false;
        } else {
            passwordBox.setError(null);
            return true;
        }
    }

    private boolean checkInput() {
        if (checkName() && checkSurname() && checkMail() && checkPassword()) {
            return true;
        }
        return false;
    }

}
