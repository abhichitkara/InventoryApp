package com.example.abhi.inventoryapp.Particulars;

import android.provider.BaseColumns;

public class EntitiesContract {


    public EntitiesContract() {
    }

    public static final class EntitiesEntry implements BaseColumns {

        public static final String TABLE_NAME = "Inventory_Table";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ARTICLE_NAME = "Article_Name";
        public static final String COLUMN_ARTICLE_PRICE = "Article_Price";
        public static final String COLUMN_ARTICLE_QUANTITY = "Article_Quantity";
        public static final String COLUMN_SELLER_NAME = "Seller_Name";
        public static final String COLUMN_SELLER_PHONE = "Seller_Phone_Number";
        public static final String COLUMN_SELLER_EMAIL = "Seller_Email";
        public static final String COLUMN_ARTICLE_IMAGE = "Article";

        public static final String CREATE_TABLE_INVENTORY = "CREATE TABLE " +
                EntitiesContract.EntitiesEntry.TABLE_NAME + "(" +
                EntitiesContract.EntitiesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_NAME + " TEXT NOT NULL," +
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_PRICE + " TEXT NOT NULL," +
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_QUANTITY + " INTEGER NOT NULL DEFAULT 0," +
                EntitiesContract.EntitiesEntry.COLUMN_SELLER_NAME + " TEXT NOT NULL," +
                EntitiesContract.EntitiesEntry.COLUMN_SELLER_PHONE + " TEXT NOT NULL," +
                EntitiesContract.EntitiesEntry.COLUMN_SELLER_EMAIL + " TEXT NOT NULL," +
                EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_IMAGE + " TEXT NOT NULL" + ");";
    }
}
