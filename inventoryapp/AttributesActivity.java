package com.example.abhi.inventoryapp;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.abhi.inventoryapp.Particulars.Entities;
import com.example.abhi.inventoryapp.Particulars.EntitiesContract;
import com.example.abhi.inventoryapp.Particulars.InventoryDatabaseHelper;

public class AttributesActivity extends AppCompatActivity {

    private static final int Permissions_Request_Data_Access = 1;
    private static final int Select_Image_Request = 0;
    EditText articlenameEditText;
    EditText articlepriceEditText;
    EditText articlequantityEditText;
    EditText sellernameEditText;
    EditText sellerphonenoEditText;
    EditText selleremailEditText;
    ImageButton articlequantitydecrease;
    ImageButton articlequantityincrease;
    Button articleimagebtn;
    ImageView articleimageview;
    Uri originalUri;
    Boolean entityinfochanged = false;
    long currententityid;
    private InventoryDatabaseHelper inventoryhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attributes);

        articlenameEditText = (EditText) findViewById(R.id.article_name_edittext);
        articlepriceEditText = (EditText) findViewById(R.id.article_price_edittext);
        articlequantityEditText = (EditText) findViewById(R.id.quantity_edittext_article);
        sellernameEditText = (EditText) findViewById(R.id.seller_name_edittext);
        sellerphonenoEditText = (EditText) findViewById(R.id.seller_phone_edittext);
        selleremailEditText = (EditText) findViewById(R.id.seller_email_edittext);
        articlequantitydecrease = (ImageButton) findViewById(R.id.decrease_quantity_article);
        articlequantityincrease = (ImageButton) findViewById(R.id.increase_quantity_article);
        articleimagebtn = (Button) findViewById(R.id.article_select_image);
        articleimageview = (ImageView) findViewById(R.id.article_image_view);

        inventoryhelper = new InventoryDatabaseHelper(this);
        currententityid = getIntent().getLongExtra("EntityId", 0);

        articlequantityincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prevvalue_entity = articlequantityEditText.getText().toString();
                int previousval;
                if (prevvalue_entity.isEmpty()) {
                    previousval = 0;
                } else {
                    previousval = Integer.parseInt(prevvalue_entity);
                }
                articlequantityEditText.setText(String.valueOf(previousval + 1));
                entityinfochanged = true;
            }
        });

        articlequantitydecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prevvalue_entity = articlequantityEditText.getText().toString();
                int previousval;
                if (prevvalue_entity.isEmpty()) {
                    return;
                } else if (prevvalue_entity.equals("0")) {
                    return;
                } else {
                    previousval = Integer.parseInt(prevvalue_entity);
                    articlequantityEditText.setText(String.valueOf(previousval - 1));
                }
                entityinfochanged = true;
            }
        });

        articleimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AttributesActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AttributesActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Permissions_Request_Data_Access);
                    return;
                }
                SelectImage();
                entityinfochanged = true;
            }
        });


        if (currententityid == 0) {
            setTitle(getString(R.string.addnewentity_title));
        } else {
            setTitle(getString(R.string.editentity_title));
            addValuestoEditEntity(currententityid);
        }
    }

    private void addValuestoEditEntity(long entityId) {

        Cursor cursor = inventoryhelper.read_single_entity(entityId);
        cursor.moveToFirst();

        articlenameEditText.setText(cursor.getString(cursor.getColumnIndex(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_NAME)));
        articlepriceEditText.setText(cursor.getString(cursor.getColumnIndex(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_PRICE)));
        articlequantityEditText.setText(cursor.getString(cursor.getColumnIndex(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_QUANTITY)));
        sellernameEditText.setText(cursor.getString(cursor.getColumnIndex(EntitiesContract.EntitiesEntry.COLUMN_SELLER_NAME)));
        sellerphonenoEditText.setText(cursor.getString(cursor.getColumnIndex(EntitiesContract.EntitiesEntry.COLUMN_SELLER_PHONE)));
        selleremailEditText.setText(cursor.getString(cursor.getColumnIndex(EntitiesContract.EntitiesEntry.COLUMN_SELLER_EMAIL)));
        articleimageview.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex(EntitiesContract.EntitiesEntry.COLUMN_ARTICLE_IMAGE))));

        sellerphonenoEditText.setEnabled(false);
        selleremailEditText.setEnabled(false);
        articleimagebtn.setEnabled(false);
        articlenameEditText.setEnabled(false);
        articlepriceEditText.setEnabled(false);
        sellernameEditText.setEnabled(false);

    }

    private void SelectImage() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 21) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), Select_Image_Request);
    }

    @Override
    protected void onActivityResult(int requestcode_image, int resultcode_image, Intent data) {
        if (requestcode_image == Select_Image_Request && resultcode_image == Activity.RESULT_OK) {
            if (data != null) {
                originalUri = data.getData();
                articleimageview.setImageURI(originalUri);
                articleimageview.invalidate();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestcode_image, @NonNull String[] permissions_inventory, @NonNull int[] resultsgrant) {
        switch (requestcode_image) {
            case Permissions_Request_Data_Access: {
                if (resultsgrant.length > 0
                        && resultsgrant[0] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!entityinfochanged) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardchangesButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialoginterface_inventory, int entity_id) {
                        finish();
                    }
                };
        EntityUnsavedChangesDialogBox(discardchangesButtonClickListener);
    }

    private void EntityUnsavedChangesDialogBox(
            DialogInterface.OnClickListener discardchangesButtonClickListener) {
        AlertDialog.Builder builderinventory = new AlertDialog.Builder(this);
        builderinventory.setMessage(R.string.attributes_dialog_unsaved_changes_msg);
        builderinventory.setPositiveButton(R.string.dialog_discard_button, discardchangesButtonClickListener);
        builderinventory.setNegativeButton(R.string.dialog_keepediting_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginventory, int id) {
                if (dialoginventory != null) {
                    dialoginventory.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builderinventory.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu_attributes) {
        getMenuInflater().inflate(R.menu.inventoryattributesmenu, menu_attributes);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu_attributes) {
        super.onPrepareOptionsMenu(menu_attributes);
        if (currententityid == 0) {
            MenuItem deletesingleentity = menu_attributes.findItem(R.id.task_delete_entity);
            MenuItem clearalldata = menu_attributes.findItem(R.id.task_clear_all_data);
            MenuItem orderentity = menu_attributes.findItem(R.id.task_buy_entity);
            deletesingleentity.setVisible(false);
            clearalldata.setVisible(false);
            orderentity.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuentity) {
        switch (menuentity.getItemId()) {
            case R.id.task_save_entity:
                if (!add_entity_to_inventory_database()) {
                    return true;
                }
                finish();
                return true;

            case R.id.task_buy_entity:
                article_order_confirmation_dialog();
                return true;

            case R.id.task_delete_entity:
                entity_delete_dialog(currententityid);
                return true;

            case R.id.task_clear_all_data:
                entity_delete_dialog(0);
                return true;

            case android.R.id.home:
                if (!entityinfochanged) {
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonchangesClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialoginterfaceinventory, int entity_id) {
                                NavUtils.navigateUpFromSameTask(AttributesActivity.this);
                            }
                        };
                EntityUnsavedChangesDialogBox(discardButtonchangesClickListener);
                return true;


        }
        return super.onOptionsItemSelected(menuentity);
    }

    private boolean add_entity_to_inventory_database() {
        boolean allvaluescorrectcheck = true;
        if (!tocheckifentityvaluecorrect(articlenameEditText, "name")) {
            allvaluescorrectcheck = false;
        }
        if (!tocheckifentityvaluecorrect(articlepriceEditText, "price")) {
            allvaluescorrectcheck = false;
        }
        if (!tocheckifentityvaluecorrect(articlequantityEditText, "quantity")) {
            allvaluescorrectcheck = false;
        }
        if (!tocheckifentityvaluecorrect(selleremailEditText, "supplier name")) {
            allvaluescorrectcheck = false;
        }
        if (!tocheckifentityvaluecorrect(sellerphonenoEditText, "supplier phone")) {
            allvaluescorrectcheck = false;
        }
        if (!tocheckifentityvaluecorrect(selleremailEditText, "supplier email")) {
            allvaluescorrectcheck = false;
        }
        if (currententityid == 0 && originalUri == null) {
            allvaluescorrectcheck = false;
            articleimagebtn.setError("Missing Article Image");
        }
        if (!allvaluescorrectcheck) {
            return false;
        }

        if (currententityid == 0) {
            Entities entities = new Entities(
                    articlenameEditText.getText().toString().trim(),
                    articlepriceEditText.getText().toString().trim(),
                    Integer.parseInt(articlequantityEditText.getText().toString().trim()),
                    sellernameEditText.getText().toString().trim(),
                    sellerphonenoEditText.getText().toString().trim(),
                    sellernameEditText.getText().toString().trim(),
                    originalUri.toString());
            inventoryhelper.InsertEntity(entities);
        } else {
            int article_quantity = Integer.parseInt(articlequantityEditText.getText().toString().trim());
            inventoryhelper.UpdateEntity(currententityid, article_quantity);
        }
        return true;
    }

    private boolean tocheckifentityvaluecorrect(EditText article_text, String article_description) {
        if (TextUtils.isEmpty(article_text.getText())) {
            article_text.setError("Missing Article " + article_description);
            return false;
        } else {
            article_text.setError(null);
            return true;
        }
    }


    private void entity_delete_dialog(final long Entity_Id) {
        AlertDialog.Builder inventorybuilder = new AlertDialog.Builder(this);
        inventorybuilder.setMessage(R.string.article_delete_dialog_msg);
        inventorybuilder.setPositiveButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog_inventory, int entity_id) {
                if (Entity_Id == 0) {
                    deleteAllEntities();
                } else {
                    deleteSingleEntity(Entity_Id);
                }
                finish();
            }
        });
        inventorybuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog_inventory, int entity_id) {
                if (dialog_inventory != null) {
                    dialog_inventory.dismiss();
                }
            }
        });
        AlertDialog alertDialog = inventorybuilder.create();
        alertDialog.show();
    }

    private int deleteAllEntities() {
        SQLiteDatabase inventorydatabase = inventoryhelper.getWritableDatabase();
        return inventorydatabase.delete(EntitiesContract.EntitiesEntry.TABLE_NAME, null, null);
    }

    private int deleteSingleEntity(long Entity_Id) {
        SQLiteDatabase inventory_database = inventoryhelper.getWritableDatabase();
        String selection_inventory = EntitiesContract.EntitiesEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(Entity_Id)};
        int EntitiesDeleted = inventory_database.delete(
                EntitiesContract.EntitiesEntry.TABLE_NAME, selection_inventory, selectionArgs);
        return EntitiesDeleted;
    }

    private void article_order_confirmation_dialog() {
        AlertDialog.Builder inventorybuilder = new AlertDialog.Builder(this);
        inventorybuilder.setMessage(R.string.dialog_order_article_msg);
        inventorybuilder.setPositiveButton(R.string.dialog_phone, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog_inventory, int Entity_Id) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + sellerphonenoEditText.getText().toString().trim()));
                startActivity(intent);
            }
        });
        inventorybuilder.setNegativeButton(R.string.dialog_email, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog_inventory, int Entity_Id) {
                Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.setData(Uri.parse("mailto:" + selleremailEditText.getText().toString().trim()));
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "New Order For Article");
                String bodyMessage = "We need more of " +
                        articlenameEditText.getText().toString().trim() +
                        " !";
                intent.putExtra(android.content.Intent.EXTRA_TEXT, bodyMessage);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = inventorybuilder.create();
        alertDialog.show();
    }
}
