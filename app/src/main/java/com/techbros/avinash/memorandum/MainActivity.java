package com.techbros.avinash.memorandum;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mlistviewNotes;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlistviewNotes = (ListView) findViewById(R.id.main_listview_notes);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_new_note:
                //start NoteActivity in NewNote mode

                startActivity(new Intent(this, NoteActivity.class));
                break;
        }
        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mlistviewNotes.setAdapter(null);

        ArrayList<Note> notes = Utilities.getAllSavedNotes(this);

        if (notes == null || notes.size() == 0) {
            Toast.makeText(this, "You Have No Saved Notes", Toast.LENGTH_SHORT).show();
            return;
        } else {
            NoteAdaptor na = new NoteAdaptor(this, R.layout.item_note, notes);
            mlistviewNotes.setAdapter(na);

            mlistviewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fileName = ((Note)mlistviewNotes.getItemAtPosition(position)).getmDateTime() + Utilities.FILE_EXTENSION;

                    Intent viewNoteIntent = new Intent(getApplicationContext(), NoteActivity.class);
                    viewNoteIntent.putExtra("NOTE_FILE", fileName);
                    startActivity(viewNoteIntent);
                }
            });

        }

    }


}
