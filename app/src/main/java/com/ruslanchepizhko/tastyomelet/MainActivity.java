package com.ruslanchepizhko.tastyomelet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends BaseActivity {
    ValueEventListener valueEventOnlMain;
    DatabaseReference connectRef = FirebaseDatabase.getInstance().getReference(".info/connected");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Убираем панель уведомлений
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        showProgressDialog();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // проверка соединения
                onConnectOnline();
                hideProgressDialog();
                MainActivity.this.finish();
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        },4000);

    }

    private void onConnectOnline(){
        // слушатель проверки соединения
        valueEventOnlMain = connectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Toast.makeText(getApplicationContext(), R.string.yes_inet, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_inet, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), R.string.no_inet, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        if(connectRef != null) {
            connectRef.removeEventListener(valueEventOnlMain);
        }
        super.onDestroy();
    }
}
