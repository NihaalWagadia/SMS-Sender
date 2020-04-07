package com.example.notificationhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floating_Message = findViewById(R.id.fab_action1);
        floating_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Messsage.class);
                startActivity(intent);
                finish();

            }
        });
        FloatingActionButton floating_Reminder = findViewById(R.id.fab_action2);
        floating_Reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Reminder.class);
                startActivity(intent);
                finish();

            }
        });
    }

}
