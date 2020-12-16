package com.BlueHat.mynoteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNew, btnOpen, btnSave;
    EditText etShow, etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNew = findViewById(R.id.btnNewFile_main);
        btnOpen = findViewById(R.id.btnOpenFile_main);
        btnSave = findViewById(R.id.btnSave_main);
        etShow = findViewById(R.id.etText_main);
        etTitle = findViewById(R.id.etFileName_main);

        btnSave.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnNew.setOnClickListener(this);
    }

    private void newFile(){
        etShow.setText("");
        etTitle.setText("");
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    private void showList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, getFilesDir().list());
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void loadData(String title) {
        FileModel fileModel = FileHelper.readFromFile(this, title);
        etTitle.setText(fileModel.getFilename());
        etShow.setText(fileModel.getData());
        Toast.makeText(this, "Loading " + fileModel.getFilename() + " data", Toast.LENGTH_SHORT).show();
    }
    private void saveFile() {
        if (etTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else if (etShow.getText().toString().isEmpty()) {
            Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else {
            String title = etTitle.getText().toString();
            String text = etShow.getText().toString();
            FileModel fileModel = new FileModel();
             fileModel.setFilename(title);
             fileModel.setData(text);
             FileHelper.writeToFile(fileModel, this);
             Toast.makeText(this, "Saving " + fileModel.getFilename()+" file", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnNewFile_main:
                newFile();
                break;
            case R.id.btnOpenFile_main:
                showList();
                break;
            case R.id.btnSave_main:
                saveFile();
                break;
        }
    }

}