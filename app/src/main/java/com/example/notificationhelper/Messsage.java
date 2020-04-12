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
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Messsage extends AppCompatActivity {

    int LAUNCH_SECOND_ACTIVITY=1;
    EditText text_pNumber, text_message;
    ImageView imageView;
    ChipGroup chipGroup;
    RecyclerView recyclerView;
    ArrayList<String> retrieve_contacts = new ArrayList<>();
    HashMap<String, String>hashMap_retrieve = new HashMap<>();
    ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_messsage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chipGroup = findViewById(R.id.chip_group);
        text_message = findViewById(R.id.text_message);
        text_message.setMovementMethod(new ScrollingMovementMethod());
        text_pNumber = findViewById(R.id.text_phone_number);
        imageView = findViewById(R.id.Contact_image_clickable);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hashMap_retrieve!=null){
                    Intent intent = new Intent(Messsage.this, ContactsList.class);
                    intent.putExtra("ContactsSelected", hashMap_retrieve);
                    setResult(Activity.RESULT_OK, intent);
                    startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
                }
                else {
                    Intent intent = new Intent(Messsage.this, ContactsList.class);
                    startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);

                }


            }
        });

//        retrieve_contacts = getIntent().getStringArrayListExtra("SelectedContact");
//        if(retrieve_contacts!=null) {
//            loadChips();
//        }
        hashMap_retrieve = (HashMap<String, String>)getIntent().getSerializableExtra("SelectedContact");
        if(hashMap_retrieve!=null) {

            loadChips();
        }

    }

    private void loadChips() {
        for (Map.Entry<String, String> entry : hashMap_retrieve.entrySet()) {
            list.add(entry.getKey());
            //String v = entry.getValue();
            //System.out.println("Key: " + k + ", Value: " + v);
        }
        LayoutInflater inflater = LayoutInflater.from(Messsage.this);
        for(final String str : list){
            Chip chip = (Chip) inflater.inflate(R.layout.chip, null, false);
            chip.setText(hashMap_retrieve.get(str));
           // Log.d("LISTVALUES", String.valueOf(retrieve_contacts));
          //  final int finalI = i;
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chipGroup.removeView(view);
                    //retrieve_contacts.remove(finalI);
                    hashMap_retrieve.remove(str);
                }
            });
            chipGroup.addView(chip);
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
