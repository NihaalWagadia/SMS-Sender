package com.example.notificationhelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notificationhelper.Adapters.MyAdapter;
import com.example.notificationhelper.Model.MyContacts;

import java.util.ArrayList;

public class ContactsList extends AppCompatActivity implements checkBoxListener {

    RecyclerView recyclerView;
    ArrayList<MyContacts>myContactsArrayList ;
    ArrayList<String> ll = new ArrayList<>();
    ArrayList<MyContacts> arrayList = new ArrayList<>();
    Button selected_cont;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view_contacts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadContacts();
        selected_cont = findViewById(R.id.selectedContact);
        selected_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returning = new Intent(view.getContext(), Messsage.class);
                returning.putStringArrayListExtra("SelectedContact", ll);
                setResult(Activity.RESULT_OK, returning);
                startActivity(returning);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent return_intent = new Intent(this, Messsage.class);
        return_intent.putExtra("SelectedContact", ll);
        setResult(Activity.RESULT_OK, return_intent);
        startActivity(return_intent);
        finish();
    }

    private void loadContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone
                .CONTENT_URI, null, null, null, null);
        startManagingCursor(cursor);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if(number.length()>0){
                    Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);

                    if(phoneCursor.getCount()>0){
                        while (phoneCursor.moveToNext()){
                            String phoneNumber_value = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            MyContacts myContacts = new MyContacts(name, phoneNumber_value,false);
                            arrayList.add(myContacts);
                        }
                    }
                    phoneCursor.close();
                }
            }

            //initialize adapter and set it to recyclerview

            MyAdapter myAdapter = new MyAdapter(this, arrayList);
            recyclerView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();

        }
        else {
            Toast.makeText(getApplicationContext(),"NO CONTACTS FOUND", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
        public void stateChange(int position, boolean checked) {
            if(checked){
                ll.add(String.valueOf(((arrayList.get(position).getName()))));
                Log.d(String.valueOf(ll),"ADD ZALA");
                Log.d("CHECKING", String.valueOf(ll));
            }
            else{
                ll.remove(arrayList.get(position));
            }

        }

}
