package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.data.Databasehandler;
import com.example.myapplication.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button save;
    private EditText baby_item;
    private EditText quantity;
    private EditText size;
    private EditText color;
    Databasehandler databasehandler =new Databasehandler(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       bypassActivity();
        List<Item> items = databasehandler.getAllItems();
        for(Item item : items){
            Log.d("Main", "onCreate: " + item.getItemname());
        }
       ;




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
               createPopupdialog();

            }
        });
    }

    private void bypassActivity() {
        if (databasehandler.getItemsCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }

    private void createPopupdialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup , null);
        baby_item= view.findViewById(R.id.enteritem);
        quantity = view.findViewById(R.id.quantity);
        size=view.findViewById(R.id.size);
        color = view.findViewById(R.id.colour);
        builder.setView(view);

        //invoking alertdialog
        dialog = builder.create();  //creating dialog object
        dialog.show();
        save = view.findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!baby_item .getText().toString().isEmpty()
                        && !color.getText().toString().isEmpty()
                        && !quantity.getText().toString().isEmpty()
                        && !size.getText().toString().isEmpty()){
                Item item = new Item();
                String newItem = baby_item.getText().toString().trim();
                String newColor = color.getText().toString().trim();
                int newquantity = Integer.parseInt(quantity.getText().toString().trim());
                int newsize = Integer.parseInt(size.getText().toString().trim());

                item.setItemname(newItem);
                item.setItemsize(newsize);
                item.setItemcolour(newColor);
                item.setItemquantity(newquantity);
                 databasehandler.addItem(item);}
                else {
                    Snackbar.make(view , "Every field should be filled", Snackbar.LENGTH_SHORT)
                            .show();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      dialog.dismiss();
                      startActivity(new Intent(MainActivity.this , ListActivity.class));
                       finish();
                    }
                }, 1200);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}