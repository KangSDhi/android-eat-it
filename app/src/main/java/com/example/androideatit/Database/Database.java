package com.example.androideatit.Database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.androideatit.Model.ModelOrder;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "EatItDB.db";
    private static final int DB_VER = 1;
    public Database(Context context){
        super(context, DB_NAME, null, DB_VER);
    }

    public List<ModelOrder> getCarts()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null, null, null, null, null);

        final List<ModelOrder> result = new ArrayList<>();
        if (c.moveToFirst())
        {
            do {
                result.add(new ModelOrder(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))
                ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(ModelOrder modelOrder)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
                modelOrder.getProdukId(),
                modelOrder.getProdukNama(),
                modelOrder.getKuantitas(),
                modelOrder.getHarga(),
                modelOrder.getDiskon());
        db.execSQL(query);

    }

    public void cleanCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

}
