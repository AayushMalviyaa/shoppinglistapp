package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.data.Databasehandler;
import com.example.myapplication.data.RecylerviewAdapter;
import com.example.myapplication.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab ;
    Databasehandler databaseHandler;
    List<Item> itemList;
    RecylerviewAdapter recyclerViewAdapter;
    private Button save;
    private EditText baby_item;
    private EditText quantity;
    private EditText size;
    private EditText color;
    AlertDialog dialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recy);
        fab = findViewById(R.id.floatingActionButton2);
        databaseHandler = new Databasehandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();


        //Get items from db
        itemList = databaseHandler.getAllItems();

        for (Item item : itemList) {

            Log.d("hi", "onCreate: " + item.getItemcolour());

        }

        recyclerViewAdapter = new RecylerviewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(ListActivity.this);
                 view = getLayoutInflater().inflate(R.layout.popup, null);
                baby_item = view.findViewById(R.id.enteritem);
                quantity = view.findViewById(R.id.quantity);
                size = view.findViewById(R.id.size);
                color = view.findViewById(R.id.colour);
                builder.setView(view);

                //invoking alertdialog
                dialog = builder.create();  //creating dialog object
                dialog.show();
                save = view.findViewById(R.id.button2);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!baby_item.getText().toString().isEmpty()
                                && !color.getText().toString().isEmpty()
                                && !quantity.getText().toString().isEmpty()
                                && !size.getText().toString().isEmpty()) {
                            Item item = new Item();
                            String newItem = baby_item.getText().toString().trim();
                            String newColor = color.getText().toString().trim();
                            int newquantity = Integer.parseInt(quantity.getText().toString().trim());
                            int newsize = Integer.parseInt(size.getText().toString().trim());

                            item.setItemname(newItem);
                            item.setItemsize(newsize);
                            item.setItemcolour(newColor);
                            item.setItemquantity(newquantity);
                            databaseHandler.addItem(item);
                        } else {
                            Snackbar.make(view, "Every field should be filled", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
               new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        startActivity(new Intent(ListActivity.this , ListActivity.class));
                         finish();
                    }
                }, 1200);
            }

                });
            }
        });

    }


   }

