package com.example.notificationhelper;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notificationhelper.Adapters.MyAdapter;
import com.example.notificationhelper.Model.MyContacts;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ContactsList extends AppCompatActivity implements checkBoxListener {

    RecyclerView recyclerView;
    ArrayList<MyContacts>myContactsArrayList ;
    ArrayList<String> ll = new ArrayList<>();
    ArrayList<String> ll2 = new ArrayList<>();
    HashMap<String, String>hashMap = new HashMap<>();
    HashMap<String, String>hashMap2 = new HashMap<>();
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
        //ll2 = getIntent().getStringArrayListExtra("ContactsSelected");
        hashMap2 =  (HashMap<String, String>)getIntent().getSerializableExtra("ContactsSelected");
        loadContacts();
        selected_cont = findViewById(R.id.selectedContact);
        Log.d("PRINT", String.valueOf(ll2));


        selected_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returning = new Intent(view.getContext(), Messsage.class);
//                if(ll2!=null){
//                    for(int i=0; i<ll2.size(); i++){
//                        if(ll.contains(ll2))
//                        ll.add(ll2.get(i));
//                    }
//                }
//                if (hashMap2!=null){
//                    for (Map.Entry<String, String> entry : hashMap2.entrySet()) {
//                        String v = entry.getValue();
//                        String k = entry.getKey();
//
//                        hashMap.put(k,v);
//                        //String v = entry.getValue();
//                        //System.out.println("Key: " + k + ", Value: " + v);
//                    }
//                }

                //returning.putStringArrayListExtra("SelectedContact", ll);
                returning.putExtra("SelectedContact", hashMap);
                setResult(Activity.RESULT_OK, returning);
                startActivity(returning);
                finish();
            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        Intent return_intent = new Intent(this, Messsage.class);
//        return_intent.putExtra("SelectedContact", ll);
//        setResult(Activity.RESULT_OK, return_intent);
//        startActivity(return_intent);
//        finish();
//    }

    private void loadContacts() {
//        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone
//                .CONTENT_URI, null, null, null, null);
//        startManagingCursor(cursor);
        String phoneNumber = null;
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);


        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
//                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String contact_name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

                if(hasPhoneNumber>0){
//                    Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                        while (phoneCursor.moveToNext()){
                           // String phoneNumber_value = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                            MyContacts myContacts = new MyContacts(contact_name, phoneNumber, false);
                          //  Log.d("PROXY", String.valueOf(ll2.size()));

//                            if(ll2 != null){
//                                if(ll2.contains(contact_name)){
//                                    Log.d("PRINT", contact_name);
//                                    myContacts = new MyContacts(contact_name, phoneNumber, true);
//                                }
//                            }
                            if(hashMap2 !=null){
                                if(hashMap2.containsKey(phoneNumber)){
                                    myContacts = new MyContacts(contact_name, phoneNumber, true);
                                }
                            }

                            Collections.sort(arrayList, new Comparator<MyContacts>() {
                                @Override
                                public int compare(MyContacts myContacts, MyContacts t1) {
                                    return myContacts.getName().compareTo(t1.getName());
                                }
                            });
                            arrayList.add(myContacts);
                        }

                    phoneCursor.close();
                }
            }

//            //initialize adapter and set it to recyclerview

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
//                if(!ll.contains(arrayList.get(position).getName())) {
                   // ll.add(String.valueOf(((arrayList.get(position).getName()))));
                    hashMap.put(String.valueOf(((arrayList.get(position).getNumber()))), String.valueOf(((arrayList.get(position).getName()))));
                    Log.d(String.valueOf(ll), "ADD ZALA");
                    Log.d("CHECKING", String.valueOf(ll));
                Log.d("ADDING", String.valueOf(hashMap.size()));
            }
            else{
               // ll.remove(String.valueOf(arrayList.get(position)));

                hashMap.remove(String.valueOf(((arrayList.get(position).getNumber()))));
                Log.d("DELETE", String.valueOf(hashMap.values()));
                Log.d("ADDINGS", String.valueOf(hashMap.size()));
            }

        }

}
