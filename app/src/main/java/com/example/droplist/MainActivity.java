package com.example.droplist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    List<Product> prodList = new ArrayList<Product>();
    public boolean adding = false;
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
        // Add Test Data
        //Product test = new Product("123123", "Test Product", 4);
        //prodList.add(test);
        updateDisplay();

    }



    public void onRemoveClick(){
        getEAN(false);

    }

    public void onAddClick(){
        Toast.makeText(getApplicationContext(), "Add Button Clicked", Toast.LENGTH_LONG).show();
        // TODO: Implement barcode scanner
        getEAN(true);
    }

    public void updateGrid(){
        // Do the update thingy
    }

    public void getEAN(final boolean add){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);  // optional
        integrator.setPrompt("Please scan the barcode");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
        adding = add;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
        if (adding == true) {
            getName(result.getContents());
        }else{
            removeFromList(result.getContents());
        }
    }

    public void getName(final String EAN){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);

        builder.setTitle("Insert Product Name");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                getQuant(EAN, m_Text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void getQuant(final String EAN, final String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);

        builder.setTitle("Insert Quantity");
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                addToList(EAN, name, Integer.valueOf(m_Text));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void addToList(String EAN, String name, int Quant){
        Product prod = new Product(EAN, name, Quant);
        prodList.add(prod);
        updateDisplay();
    }

    public void removeFromList(String EAN){
        int i = 0;
        for (; i < prodList.size(); i++){
            if(prodList.get(i).getEAN().equals(EAN)){
                if(prodList.get(i).getQuant() > 0){
                    prodList.get(i).setQuant(prodList.get(i).getQuant() - 1);
                    break;
                }
            }
       }
        if(prodList.get(i).getQuant() <= 0){
            prodList.remove(i);
        }
        updateDisplay();
    }

    public void updateDisplay(){
        List<String> display = new ArrayList<>();
        for(int i = 0; i < prodList.size() + 1; i++) {
            if (i == 0) {
                display.add("EAN");
                display.add("Name");
                display.add("Quantity");
            } else {
                display.add(prodList.get(i - 1).getEAN());
                display.add((prodList.get(i - 1).getName()));
                display.add(String.valueOf(prodList.get(i - 1).getQuant()));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, display);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(adapter);

    }
}
