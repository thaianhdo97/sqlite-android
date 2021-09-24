package com.example.sqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.sqlite.R;
import com.example.sqlite.database.DBHelper;

public class ListUserActivity extends AppCompatActivity {

    private DBHelper db;
    private Cursor c;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        db = new DBHelper(this);
        ListView lvUser = findViewById(R.id.lvUser);

        c = db.getAllUser();

        adapter = new SimpleCursorAdapter(this, R.layout.item_user, c,
                new String[]{DBHelper.ID, DBHelper.NAME, DBHelper.GENDER},
                new int[]{R.id.tvId, R.id.tvName, R.id.tvGender},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        lvUser.setAdapter(adapter);
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                int _id = cursor.getInt(cursor.getColumnIndex(DBHelper.ID));
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME));
                String gender = cursor.getString(cursor.getColumnIndex(DBHelper.GENDER));
                String des = cursor.getString(cursor.getColumnIndex(DBHelper.DES));

                Intent intent = new Intent(ListUserActivity.this, UpdateActivity.class);
                intent.putExtra(DBHelper.ID, _id);
                intent.putExtra(DBHelper.NAME, name);
                intent.putExtra(DBHelper.GENDER, gender);
                intent.putExtra(DBHelper.DES, des);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Reload data in list view
        c = db.getAllUser();
        adapter.changeCursor(c);
        adapter.notifyDataSetChanged();
        db.close();
    }
}