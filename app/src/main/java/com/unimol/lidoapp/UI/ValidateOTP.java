package com.unimol.lidoapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

public class ValidateOTP extends AppCompatActivity {
    private EditText otpBox;
    private Button insertOtp;
    private TextView newPasswordLink;
    private String validateOTPUrl = "validateotp/dovalidateotp?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validateotp);

        this.otpBox = (EditText) findViewById(R.id.otpBox);
        this.insertOtp = (Button) findViewById(R.id.otpButton);
        this.newPasswordLink = (TextView) findViewById(R.id.setNewPassword);

        this.newPasswordLink.setVisibility(View.INVISIBLE);

        this.insertOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOTP()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL(otpBox.getText().toString().trim(), Preferences.getOTPFromPreferences(ValidateOTP.this)), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("true")) {
                                Toast.makeText(ValidateOTP.this, "CODICE OTP VALIDATO", Toast.LENGTH_LONG).show();
                                newPasswordLink.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(ValidateOTP.this, "IL CODICE OTP E' SBAGLIATO", Toast.LENGTH_LONG).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(ValidateOTP.this, "Errore di connessione -> " + volleyError, Toast.LENGTH_LONG).show();

                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(ValidateOTP.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
        this.newPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ValidateOTP.this, ChangePassword.class));
            }
        });


    }

    private String completeURL(String insertedOtp, String sendedOtp) {
        return Constants.URLREST.concat(this.validateOTPUrl).concat("insertedotp=" + insertedOtp + "&sendedotp=" + sendedOtp);
    }

    private boolean checkOTP() {
        String otp = otpBox.getText().toString().trim();
        if (otp.isEmpty()) {
            otpBox.setError("Il campo non può essere vuoto");
            return false;
        } else {
            otpBox.setError(null);
            return true;
        }
    }
}
