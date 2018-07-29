package com.example.abhi.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.abhi.inventoryapp.Particulars.Entities;
import com.example.abhi.inventoryapp.Particulars.InventoryDatabaseHelper;


public class MainActivity extends AppCompatActivity {

    EntitiesCursorAdapter inventoryadapter;
    InventoryDatabaseHelper inventoryhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inventoryhelper = new InventoryDatabaseHelper(this);

        final FloatingActionButton floatingactionbtn = (FloatingActionButton) findViewById(R.id.floatingactionbtn);
        floatingactionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AttributesActivity.class);
                startActivity(i);
            }
        });


        final ListView listview_inventory = (ListView) findViewById(R.id.entitylistview);
        View emptyView = findViewById(R.id.emptyviewinventory);
        listview_inventory.setEmptyView(emptyView);

        Cursor cursor = inventoryhelper.ReadData();

        inventoryadapter = new EntitiesCursorAdapter(this, cursor);
        listview_inventory.setAdapter(inventoryadapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inventoryadapter.swapCursor(inventoryhelper.ReadData());
    }

    public void onClickEntityView(long article_id) {
        Intent intent = new Intent(this, AttributesActivity.class);
        intent.putExtra("EntityId", article_id);
        startActivity(intent);
    }

    public void TransactionOnClick(long article_id, int article_quantity) {
        inventoryhelper.singleentitytransaction(article_id, article_quantity);
        inventoryadapter.swapCursor(inventoryhelper.ReadData());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu_inventory) {
        getMenuInflater().inflate(R.menu.inventorymainmenu, menu_inventory);
        return super.onCreateOptionsMenu(menu_inventory);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem entity) {
        switch (entity.getItemId()) {
            case R.id.task_add_sample_data:
                addSampleData();
                inventoryadapter.swapCursor(inventoryhelper.ReadData());
        }
        return super.onOptionsItemSelected(entity);
    }

    private void addSampleData() {
        Entities VitaminWater = new Entities(
                "Wild Water",
                "₹45",
                70,
                "Beltek Canadian Water",
                "+91 011 2811 7666",
                "contact@drinkwildwater.com",
                "android.resource://com.example.abhi.inventoryapp/drawable/wildwater");
        inventoryhelper.InsertEntity(VitaminWater);

        Entities Gatorade = new Entities(
                "Gatorade Blue Bolt",
                "₹35",
                25,
                "Pepsico India",
                "1800 22 40120",
                "consumer.feedback@pepsico.com",
                "android.resource://com.example.abhi.inventoryapp/drawable/gatorade");
        inventoryhelper.InsertEntity(Gatorade);

        Entities RedBull = new Entities(
                "RedBull",
                "₹105",
                10,
                "RedBullIndia",
                "+91 020 3065 5496",
                "consume.support@redbullindia.com",
                "android.resource://com.example.abhi.inventoryapp/drawable/redbull");
        inventoryhelper.InsertEntity(RedBull);

        Entities IceCreamSandwich = new Entities(
                "Crazy Cookie Cake",
                "₹360",
                50,
                "HavmorIndia",
                "+91 79 30909000",
                "www.havmor.com",
                "android.resource://com.example.abhi.inventoryapp/drawable/ccc");
        inventoryhelper.InsertEntity(IceCreamSandwich);

        Entities galaxy = new Entities(
                "Galaxy Fruit&Nut",
                "₹40",
                92,
                "Mars Incorportaed",
                "(703) 821-4900",
                "consumer.support@marsglobal.com",
                "android.resource://com.example.abhi.inventoryapp/drawable/galaxy");
        inventoryhelper.InsertEntity(galaxy);
    }

}



