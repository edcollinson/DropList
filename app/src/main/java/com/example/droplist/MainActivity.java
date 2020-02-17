package com.example.droplist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Product> prodList = new ArrayList<Product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Do button init
        Button add= (Button) findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onAddClick();
            }
        });

        Button remove= (Button) findViewById(R.id.removeButton);
        remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRemoveClick();
            }
        });
    }



    public void onRemoveClick(){
        Toast.makeText(getApplicationContext(), "Remove Button Clicked", Toast.LENGTH_LONG);
        // TODO: Implement Array Removal algo
    }

    public void onAddClick(){
        Toast.makeText(getApplicationContext(), "Add Button Clicked", Toast.LENGTH_LONG);
        // TODO: Implement barcode scanner
    }

    public void updateGrid(){
        // Do the update thingy
    }
}
