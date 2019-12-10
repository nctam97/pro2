package com.example.ktcc_get;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    static  final String AUTHOR="com.example.ktcc";
    static  final String PATH="qlnv";
    static  final String URL ="content://"+AUTHOR+"/"+PATH;
    static  final Uri CONTENTURL=Uri.parse(URL);
    Button btn_exit,btn_select,btn_delete,btn_update,btn_save;
    EditText edt_id,edt_name,edt_address;
    GridView gridView;
    ArrayList<String> lst = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_exit=findViewById(R.id.btnExit);
        btn_delete=findViewById(R.id.btnDelete);
        btn_select=findViewById(R.id.btnSelect);
        btn_update=findViewById(R.id.btnUpdate);
        btn_save=findViewById(R.id.btn_Savebook);
        edt_address=findViewById(R.id.edtaddress);
        edt_id=findViewById(R.id.edtid);
        edt_name=findViewById(R.id.edtname);
        gridView=findViewById(R.id.grvDisplay);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_id.getText().toString().isEmpty()){
                    Cursor cursor = getContentResolver().query(CONTENTURL, null, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        lst=new ArrayList<>();
                        do {

                            lst.add(cursor.getInt(0) + "");
                            lst.add(cursor.getString(1));
                            lst.add(cursor.getString(2) + "");
                        }
                        while (cursor.moveToNext());
                        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, lst);
                        gridView.setAdapter(adapter);
                    }
                }
                else {
                    int id=Integer.parseInt(edt_id.getText().toString());
                    Cursor cursor = getContentResolver().query(CONTENTURL, null, " id=?", new String[]{String.valueOf(id)}, null);
                    if (cursor != null) {
                        cursor.moveToFirst();

                            edt_id.setText(cursor.getInt(0) + "");
                            edt_name.setText(cursor.getString(1));
                            edt_address.setText(cursor.getString(2) + "");
                    }
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("id", Integer.parseInt(edt_id.getText().toString()));
                values.put("name", edt_name.getText().toString());
                values.put("address", edt_address.getText().toString());
                Uri insert = getContentResolver().insert(CONTENTURL, values);
                Toast.makeText(MainActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=Integer.parseInt(edt_id.getText().toString());
                int count=getContentResolver().delete(CONTENTURL,"id=?",new  String[]{String.valueOf(id)});
                if(count>0){
                    Toast.makeText(MainActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues content=new ContentValues();
                content.put("id",edt_id.getText().toString());
                content.put("name",edt_name.getText().toString());
                content.put("address",edt_address.getText().toString());
                int count =getContentResolver().update(CONTENTURL,content,"id=?",new String[]{edt_id.getText().toString()});
                if(count>0){
                    Toast.makeText(MainActivity.this, "Update thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
