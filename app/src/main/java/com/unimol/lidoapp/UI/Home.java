package com.unimol.lidoapp.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.unimol.lidoapp.R;
import com.unimol.lidoapp.app.Preferences;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);

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
                startActivity(new Intent(Home.this, Login.class));

                return true;

            case R.id.prenota:

                startActivity(new Intent(this, Reserve.class));

                return true;

            case R.id.home:

                return false;

            case R.id.userinfo:

                startActivity(new Intent(Home.this, UserInfo.class));

                return true;

            case R.id.deleteuser:

                startActivity(new Intent(Home.this, DeleteUser.class));

                return true;

            case R.id.deletereservation:

                startActivity(new Intent(Home.this, DeleteReservation.class));

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }
}
