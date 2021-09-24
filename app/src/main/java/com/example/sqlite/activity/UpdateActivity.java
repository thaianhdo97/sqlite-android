package com.example.sqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite.R;
import com.example.sqlite.database.DBHelper;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private DBHelper db;
    private int _id;
    private EditText edName;
    private EditText edGender;
    private EditText edDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = new DBHelper(this);

        initView();

        // Get data from ListUserActivity
        Intent intent = getIntent();
        _id = intent.getIntExtra(DBHelper.ID, 0);
        String name = intent.getStringExtra(DBHelper.NAME);
        String gender = intent.getStringExtra(DBHelper.GENDER);
        String des = intent.getStringExtra(DBHelper.DES);

        edName.setText(name);
        edGender.setText(gender);
        edDes.setText(des);
    }

    private void initView() {
        edName = findViewById(R.id.edName);
        edGender = findViewById(R.id.edGender);
        edDes = findViewById(R.id.edDes);
        Button btnUpdate = findViewById(R.id.btUpdate);
        btnUpdate.setOnClickListener(this);
        Button btnDelete = findViewById(R.id.btDelete);
        btnDelete.setOnClickListener(this);
    }

    private void onUpdate() {
        String isUpdate = db.updateUser(_id, edName.getText().toString(),
                edGender.getText().toString() + " update", edDes.getText().toString());
        Toast.makeText(this, isUpdate, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void onDelete() {
        Toast.makeText(this, db.deleteUser(_id), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btUpdate:
                onUpdate();
                break;
            case R.id.btDelete:
                onDelete();
                break;
        }
    }
}