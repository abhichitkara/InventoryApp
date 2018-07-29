package com.example.abhi.inventoryapp.Particulars;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InventoryDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "InventoryDatabase";
    public static final int DATABASE_VERSION = 3;

    public InventoryDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void InsertEntity(Entities Entity) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();

        contentvalues.put(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_NAME, Entity.getArticleName());
        contentvalues.put(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_PRICE, Entity.getArticlePrice());
        contentvalues.put(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_QUANTITY, Entity.getArticleQuantity());
        contentvalues.put(EntitiesContract.EntitiesEntry.COLUMN_SELLER_NAME, Entity.getSellerName());
        contentvalues.put(EntitiesContract.EntitiesEntry.COLUMN_SELLER_PHONE, Entity.getSellerPhoneNumber());
        contentvalues.put(EntitiesContract.EntitiesEntry.COLUMN_SELLER_EMAIL, Entity.getSellerEmail());
        contentvalues.put(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_IMAGE, Entity.getArticleImage());

        db.insert(EntitiesContract.EntitiesEntry.TABLE_NAME, null, contentvalues);
    }

    public Cursor ReadData() {
        SQLiteDatabase db = getReadableDatabase();

        String[] inventory_projection = {
                EntitiesContract.EntitiesEntry._ID,
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_NAME,
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_PRICE,
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_QUANTITY,
                EntitiesContract.EntitiesEntry.COLUMN_SELLER_NAME,
                EntitiesContract.EntitiesEntry.COLUMN_SELLER_PHONE,
                EntitiesContract.EntitiesEntry.COLUMN_SELLER_EMAIL,
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_IMAGE
        };

        Cursor cursor_inventory = db.query(
                EntitiesContract.EntitiesEntry.TABLE_NAME,
                inventory_projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor_inventory;
    }

    public Cursor read_single_entity(long entityId) {
        SQLiteDatabase db = getReadableDatabase();

        String[] inventory_projection = {
                EntitiesContract.EntitiesEntry._ID,
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_NAME,
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_PRICE,
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_QUANTITY,
                EntitiesContract.EntitiesEntry.COLUMN_SELLER_NAME,
                EntitiesContract.EntitiesEntry.COLUMN_SELLER_PHONE,
                EntitiesContract.EntitiesEntry.COLUMN_SELLER_EMAIL,
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_IMAGE
        };

        String selection_inventory = EntitiesContract.EntitiesEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(entityId)};

        Cursor cursor = db.query(
                EntitiesContract.EntitiesEntry.TABLE_NAME,
                inventory_projection,
                selection_inventory,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }

    public void UpdateEntity(long curr_Entity_Id, int quantity_article) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_QUANTITY, quantity_article);

        String selection_inventory = EntitiesContract.EntitiesEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(curr_Entity_Id)};

        db.update(EntitiesContract.EntitiesEntry.TABLE_NAME,
                values, selection_inventory, selectionArgs);
    }

    public void singleentitytransaction(long EntityId, int quantity_article) {
        SQLiteDatabase db = getWritableDatabase();
        int updated_quantity_article = 0;

        if (quantity_article > 0) {
            updated_quantity_article = quantity_article - 1;
        }

        ContentValues contentvalues = new ContentValues();
        contentvalues.put(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_QUANTITY, updated_quantity_article);

        String selection_inventory = EntitiesContract.EntitiesEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(EntityId)};

        db.update(EntitiesContract.EntitiesEntry.TABLE_NAME,
                contentvalues, selection_inventory, selectionArgs);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EntitiesContract.EntitiesEntry.CREATE_TABLE_INVENTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
