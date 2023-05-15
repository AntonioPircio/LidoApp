package com.unimol.lidoapp.UI;

import android.content.Intent;
import android.os.Bundle;
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

public class ChangePassword extends AppCompatActivity {
    private EditText passwordBox;
    private EditText confPasswordBox;
    private Button changePasswordButton;
    private String changePasswordUrl = "newpassword/donewpassword?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);

        this.passwordBox = (EditText) findViewById(R.id.passwordBox);
        this.confPasswordBox = (EditText) findViewById(R.id.confPasswordBox);
        this.changePasswordButton = (Button) findViewById(R.id.confirmPasswordButton);



        this.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, completeURL(Preferences.getMailFromPreferences(ChangePassword.this), passwordBox.getText().toString().trim(), confPasswordBox.getText().toString().trim()), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("true")) {
                                Toast.makeText(ChangePassword.this, "PASSWORD CAMBIATA CORRETTAMENTE", Toast.LENGTH_LONG).show();
                                Preferences.clearPreferences(ChangePassword.this);
                                startActivity(new Intent(ChangePassword.this, Login.class));
                            } else {
                                Toast.makeText(ChangePassword.this, "NON E' STATO POSSIBILE CAMBIARE LA PASSWORD", Toast.LENGTH_LONG).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(ChangePassword.this, "Errore di connessione -> " + volleyError, Toast.LENGTH_LONG).show();
                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private String completeURL(String mail, String password, String confPassword) {
        return Constants.URLREST.concat(this.changePasswordUrl).concat("mail=" + mail + "&newPassword=" + password + "&confPassword=" + confPassword);
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

    private boolean checkConfPassword() {
        String confPassword = confPasswordBox.getText().toString().trim();
        String password = passwordBox.getText().toString().trim();

        if (!confPassword.equals(password)) {
            confPasswordBox.setError("Le password non corrispondono");
            return false;
        } else {
            confPasswordBox.setError(null);
            return true;
        }
    }

    private boolean checkInput() {
        return this.checkPassword() && this.checkConfPassword();
    }


}
