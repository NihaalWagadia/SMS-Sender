package com.example.notificationhelper;

import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewContactList extends AppCompatActivity {
    ListView ll;
    ArrayList<String> selectedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ll = findViewById(R.id.List_Of_Contact);
        ContactsLoad();


    }

    private void ContactsLoad() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone
        .CONTENT_URI, null, null, null, null);
        startManagingCursor(cursor);

        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID};
        int[] to  = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter  simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_list, cursor, from, to);
        //ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, R.layout.rowlayout, R.id.text_lan);
        ll.setAdapter(simpleCursorAdapter);
        ll.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        ll.setOnClickListener(new  AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String selectedItem = ((TextView)view).getText().toString();
//                if(selectedItems.contains(selectedItem)){
//                    selectedItems.remove(selectedItem);
//                }
//            }
//        });

    }


}
