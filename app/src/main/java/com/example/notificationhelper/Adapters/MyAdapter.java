package com.example.notificationhelper.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notificationhelper.Model.MyContacts;
import com.example.notificationhelper.R;
import com.example.notificationhelper.checkBoxListener;

import java.util.ArrayList;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    Context context;
    ArrayList<MyContacts>myContactsArrayList;
    ArrayList<MyContacts> ll = new ArrayList<>();
    public MyAdapter(Context context, ArrayList<MyContacts> myContactsArrayList){

        this.context = context;
        this.myContactsArrayList = myContactsArrayList;

    }


    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, final int position) {
        MyContacts myContacts = myContactsArrayList.get(position);

        holder.tv_name.setText(myContacts.getName());
        holder.tv_number.setText(myContacts.getNumber());
       // MyContacts mm = myContactsArrayList.get(position);
//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CheckBox cc = (CheckBox) view;
//                MyContacts myContacts1 = (MyContacts) cc.getTag();
//                myContacts1.se
//                if(cc.isChecked()){
//                    ll.add(myContactsArrayList.get(position));
//                }
//                else if(!cc.isChecked()){
//                    ll.remove(myContactsArrayList.get(position));
//
//                }
//            }
//        });
//
        Log.d("Common fatty",String.valueOf(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ((checkBoxListener)context).stateChange(position, b);


            }
        });
        holder.checkBox.setChecked(myContacts.isSelect());
        holder.checkBox.setTag(myContacts);


    }


    @Override
    public int getItemCount() {
        return myContactsArrayList.size();
    }


    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_number;
        CheckBox checkBox;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.txt_Name);
            tv_number = itemView.findViewById(R.id.txt_phone_number);
           checkBox = itemView.findViewById(R.id.checked);




        }
    }
}
