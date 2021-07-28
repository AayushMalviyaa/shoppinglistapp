package com.example.myapplication.data;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;

public class RecylerviewAdapter extends RecyclerView.Adapter<RecylerviewAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;


    public RecylerviewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public RecylerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
View view = LayoutInflater.from(context).inflate(R.layout.row_xml , parent ,false);
        return new ViewHolder(view , context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerviewAdapter.ViewHolder viewHolder, int position) {
        Item item = itemList.get(position); // object Item

        viewHolder.itemName.setText(MessageFormat.format("Item: {0}", item.getItemname()));
        viewHolder.itemColor.setText(MessageFormat.format("Color: {0}", item.getItemcolour()));
        viewHolder.quantity.setText(MessageFormat.format("Qty: {0}", String.valueOf(item.getItemquantity())));
        viewHolder.size.setText(MessageFormat.format("Size: {0}", String.valueOf(item.getItemsize())));
        viewHolder.dateAdded.setText(MessageFormat.format("Added on: {0}", item.getDateitemadded()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder  extends  RecyclerView.ViewHolder{
        public TextView itemName;
        public TextView itemColor;
        public TextView quantity;
        public TextView size;
        public TextView dateAdded;
        public ImageButton editButton;
       public ImageButton deleteButton;
        public int id;


        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            itemName = itemView.findViewById(R.id.name);
            itemColor = itemView.findViewById(R.id.colour);
            quantity = itemView.findViewById(R.id.quantity);
            size = itemView.findViewById(R.id.size);
            dateAdded = itemView.findViewById(R.id.textView2);
             editButton = itemView.findViewById(R.id.imageButton3);
            deleteButton = itemView.findViewById(R.id.imageButton4);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Item item = itemList.get(getAdapterPosition());
                    builder = new AlertDialog.Builder(context);
                    inflater = LayoutInflater.from(context);
                    view = inflater.inflate(R.layout.popup , null);
                   final EditText baby_item= view.findViewById(R.id.enteritem);
                   final EditText quantity = view.findViewById(R.id.quantity);
                  final EditText  size=view.findViewById(R.id.size);
                   final EditText color = view.findViewById(R.id.colour);
                    Button save = view.findViewById(R.id.button2);
                    TextView title = view.findViewById(R.id.textView);

                    title.setText("Update your item here");
                    baby_item.setText(item.getItemname());
                    quantity.setText(String.valueOf(item.getItemquantity()));
                    size.setText(String.valueOf(item.getItemsize()));
                    color.setText(item.getItemcolour());
                    save.setText("Update");
                    save.setTextSize(15f);


                    builder.setView(view);
                    //invoking alertdialog
                    dialog = builder.create();  //creating dialog object
                    dialog.show();
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Databasehandler db = new Databasehandler(context);
                            item.setItemname(baby_item.getText().toString().trim());
                            item.setItemquantity(Integer.parseInt(quantity.getText().toString()));
                            item.setItemcolour(color.getText().toString().trim());
                            item.setItemsize(Integer.parseInt(size.getText().toString().trim()));

                            if (!baby_item.getText().toString().isEmpty()
                                    && !color.getText().toString().isEmpty()
                                    && !quantity.getText().toString().isEmpty()
                                    && !size.getText().toString().isEmpty()) {
                                db.updateItem(item);
                                notifyItemChanged(getAdapterPosition());
                                dialog.dismiss();
                            }
                            else {
                                Snackbar.make(view, "Every field should be filled", Snackbar.LENGTH_SHORT)
                                        .show();
                            }

                        }
                    });

                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder = new AlertDialog.Builder(context);
                    inflater = LayoutInflater.from(context);
                     view = inflater.inflate(R.layout.deleteitem , null);
                    Button yes = view.findViewById(R.id.button);
                    Button no = view.findViewById(R.id.button3);
                    builder.setView(view);
                    dialog = builder.create();
                    dialog.show();
                    yes .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = getAdapterPosition();
                            Item item = itemList.get(position);
                            Databasehandler db = new Databasehandler(context);
                            db.deleteItem(item.getId());
                            itemList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            dialog.dismiss();
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });



                }
            });

        }
    }

}
