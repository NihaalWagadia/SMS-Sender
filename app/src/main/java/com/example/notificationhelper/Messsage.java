package com.example.notificationhelper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Messsage extends AppCompatActivity {

    int LAUNCH_SECOND_ACTIVITY=1;
    EditText text_pNumber, text_message;
    ImageView imageView;
    GridView gridView;
    ArrayList<String> retrieve_contacts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_messsage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = findViewById(R.id.grid_View);
        text_message = findViewById(R.id.text_message);
        text_pNumber = findViewById(R.id.text_phone_number);
        imageView = findViewById(R.id.Contact_image_clickable);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Messsage.this, ContactsList.class);
                startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
            }
        });

        //retreive_contacts = new ArrayList<>();
        //retrieve_contacts.add("X");
        retrieve_contacts = getIntent().getStringArrayListExtra("SelectedContact");
        if(retrieve_contacts!=null) {

            Log.d("LISTVALUES", String.valueOf(retrieve_contacts));
        }
    }

    public void btn_send(View view) {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck== PackageManager.PERMISSION_GRANTED){
            MyMessage();
        }
        else{
            //if permission denied it will again ask user for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == LAUNCH_SECOND_ACTIVITY) {
                if (requestCode == Activity.RESULT_OK) {
                    String result = data.getStringExtra("SelectedContact");
                    ArrayList<String> mylist = (ArrayList<String>) getIntent().getSerializableExtra("SelectedContact");
                    Log.d("Hola", String.valueOf(mylist));
                    Log.d("Hola", result);

                }
            }

        } catch (Exception ex) {

        }
    }

    private void MyMessage() {
        String phoneNumber = text_pNumber.getText().toString().trim();
        String Message = text_message.getText().toString().trim();

        if (!text_pNumber.getText().toString().equals("") || !(text_message.getText().toString().equals(""))) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, Message, null, null);
            Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Please Enter Message or Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 0:
                if(grantResults.length>=0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    MyMessage();
                }
                else{
                    Toast.makeText(this, "You don't have permission to execute this action", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
}
