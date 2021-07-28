package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Item;
import com.example.myapplication.util.constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Databasehandler extends SQLiteOpenHelper {


    private final Context context;

    public Databasehandler(@Nullable Context context) {
        super(context, constants.DB_NAME , null , constants.DB_VERSION);
          this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_BABY_TABLE = "CREATE TABLE " + constants.TABLE_NAME + "("
                + constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + constants.KEY_BABY_ITEM + " INTEGER,"
                + constants.KEY_COLOR + " TEXT,"
                + constants.KEY_QTY_NUMBER + " INTEGER,"
                + constants.KEY_ITEM_SIZE + " INTEGER,"
                + constants.KEY_DATE_NAME + " LONG);";

        sqLiteDatabase.execSQL(CREATE_BABY_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + constants.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }


    //crud operations
    public void addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(constants.KEY_BABY_ITEM , item.getItemname());
        values.put(constants.KEY_QTY_NUMBER , item.getItemquantity());
        values.put(constants.KEY_ITEM_SIZE, item.getItemsize());
        values.put(constants.KEY_DATE_NAME , java.lang.System.currentTimeMillis());
        values.put(constants.KEY_COLOR, item.getItemcolour());

        //Inset the row
        db.insert(constants.TABLE_NAME, null, values);

        Log.d("DBHandler", "added Item: ");
    }

    public Item getItem (int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(constants.TABLE_NAME ,
                new String[]{constants.KEY_ID , constants.KEY_BABY_ITEM , constants.KEY_COLOR ,
                        constants.KEY_ITEM_SIZE , constants.KEY_QTY_NUMBER , constants.KEY_DATE_NAME}
                        , constants.KEY_ID + "=?" , new String[]{String.valueOf(id)} ,
                null , null , null);

        if (cursor != null)
            cursor.moveToFirst();

            Item item = new Item();

                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(constants.KEY_ID))));
                item.setItemname(cursor.getString(cursor.getColumnIndex(constants.KEY_BABY_ITEM)));
                item.setItemcolour(cursor.getString(cursor.getColumnIndex(constants.KEY_COLOR)));
                item.setItemquantity(cursor.getInt(cursor.getColumnIndex(constants.KEY_QTY_NUMBER)));
                item.setItemsize(cursor.getInt(cursor.getColumnIndex(constants.KEY_ITEM_SIZE)));

                //coverting timestamp into date
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formatteddate = dateFormat.format
                    (new Date(cursor.getLong(cursor.getColumnIndex(constants.KEY_DATE_NAME))).getTime());
            item.setDateitemadded(formatteddate);

return  item;

}
    public List<Item> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> itemList = new ArrayList<>();

        Cursor cursor = db.query(constants.TABLE_NAME,
                new String[]{constants.KEY_ID,
                        constants.KEY_BABY_ITEM,
                        constants.KEY_COLOR,
                        constants.KEY_QTY_NUMBER,
                        constants.KEY_ITEM_SIZE,
                        constants.KEY_DATE_NAME},
                null, null, null, null,
                constants.KEY_DATE_NAME + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(constants.KEY_ID))));
                item.setItemname(cursor.getString(cursor.getColumnIndex(constants.KEY_BABY_ITEM)));
                item.setItemcolour(cursor.getString(cursor.getColumnIndex(constants.KEY_COLOR)));
                item.setItemquantity(cursor.getInt(cursor.getColumnIndex(constants.KEY_QTY_NUMBER)));
                item.setItemsize(cursor.getInt(cursor.getColumnIndex(constants.KEY_ITEM_SIZE)));

                //convert Timestamp to something readable
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(constants.KEY_DATE_NAME)))
                        .getTime()); // Feb 23, 2020
                item.setDateitemadded(formattedDate);

                //Add to arraylist
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        return itemList;

    }

    //Todo: Add updateItem
    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(constants.KEY_BABY_ITEM, item.getItemname());
        values.put(constants.KEY_COLOR, item.getItemcolour());
        values.put(constants.KEY_QTY_NUMBER, item.getItemquantity());
        values.put(constants.KEY_ITEM_SIZE, item.getItemsize());
        values.put(constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());//timestamp of the system

        //update row
        return db.update(constants.TABLE_NAME, values,
                constants.KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())});

    }

    //Todo: Add Delete Item
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(constants.TABLE_NAME,
                constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)});

        //close
        db.close();

    }

    //Todo: getItemCount
    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

    }

}





