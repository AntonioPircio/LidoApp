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

public class ForgotPassword extends AppCompatActivity {
    private EditText mailBox;
    private TextView otpVerification;
    private Button sendMailButton;
    private String forgotPasswordUrl = "forgotpassword/doforgotpassword?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        this.mailBox = (EditText) findViewById(R.id.mailBox);
        this.otpVerification = (TextView) findViewById(R.id.otpverification);
        this.sendMailButton = (Button) findViewById(R.id.forgotPasswordButton);

        this.otpVerification.setVisibility(View.INVISIBLE);

        sendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMail()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL(mailBox.getText().toString().trim()), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.contains("false")) {
                                Preferences.saveMailPreferences(ForgotPassword.this, mailBox.getText().toString().trim());
                                Preferences.saveOTPPreferences(ForgotPassword.this, response.trim());
                                Toast.makeText(ForgotPassword.this, "CODICE OTP INVIATO ALLA TUA MAIL", Toast.LENGTH_LONG).show();
                                otpVerification.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(ForgotPassword.this, "NON SEI REGISTRATO", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(ForgotPassword.this, "Errore di connessione -> " + volleyError, Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(ForgotPassword.this);
                    requestQueue.add(stringRequest);
                }
            }
        });

        this.otpVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this, ValidateOTP.class));
            }
        });
}


    private String completeURL(String mail) {
        return Constants.URLREST.concat(this.forgotPasswordUrl).concat("mail=" + mail);
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
}
