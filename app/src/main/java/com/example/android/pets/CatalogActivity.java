/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.pets.data.PetContract;

import static com.example.android.pets.data.PetContract.PetEntry.CONTENT_URI;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.android.pets.CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        displayDatabaseInfo();
    }


    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.

        String[] projection =
                {
                        PetContract.PetEntry._ID,
                        PetContract.PetEntry.COLUMN_PET_NAME,
                        PetContract.PetEntry.COLUMN_PET_BREED,
                        PetContract.PetEntry.COLUMN_PET_GENDER,
                        PetContract.PetEntry.COLUMN_PET_WEIGHT
                };

        Cursor cursor = getContentResolver().query(CONTENT_URI, projection, null, null, null);

        try {
            // Find ListView to populate
            ListView lvItems = (ListView) findViewById(R.id.list);
        // Setup cursor adapter using cursor from last step
            PetCursorAdapter petAdapter = new PetCursorAdapter(this, cursor);
        // Attach cursor adapter to the ListView
            lvItems.setAdapter(petAdapter);

            }
            catch (Exception e)
            {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG);
            }

         finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            //cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}