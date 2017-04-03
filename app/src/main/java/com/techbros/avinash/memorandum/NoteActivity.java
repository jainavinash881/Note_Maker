package com.techbros.avinash.memorandum;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText mEtTitle;
    private EditText mEtContent;

    private String mNoteFileName;
    private Note mLoadedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mEtTitle = (EditText) findViewById(R.id.note_et_content);
        mEtContent = (EditText) findViewById(R.id.note_et_content);

        mNoteFileName = getIntent().getStringExtra("NOTE_FILE");
        if(mNoteFileName != null && !mNoteFileName.isEmpty() ) {
            mLoadedNote = Utilities.getNoteByName(this, mNoteFileName);

            if(mLoadedNote != null) {
                mEtTitle.setText(mLoadedNote.getmTitle());
                mEtContent.setText(mLoadedNote.getmContent());

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_note_save:
                saveNote();


            case R.id.action_note_delete:
                deleteNote();
                break;
        }

        return  true;
    }



    private void saveNote(){
        Note note;

        if(mEtTitle.getText().toString().trim().isEmpty() || mEtContent.getText().toString().trim().isEmpty()) {
           Toast.makeText(this, "please enter a title and content", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mLoadedNote == null) {
             note = new Note(System.currentTimeMillis(), mEtTitle.getText().toString(), mEtContent.getText().toString());
            Utilities.saveNote(this, note);
        }else {
            note = new Note(mLoadedNote.getmDateTime(), mEtTitle.getText().toString(), mEtContent.getText().toString());
            Utilities.saveNote(this, note);
        }

        if(Utilities.saveNote(this, note)) {
            Toast.makeText(this, "Your Note is Saved!", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "can not save the note, please make sure you have enough space on device", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void deleteNote() {
     if(mLoadedNote == null) {
         finish();
     }else {

         AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                 .setTitle("delete")
                 .setMessage("You are about to delete a note" + mEtTitle.getText().toString() + ",are you sure?")
                 .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 Utilities.deleteNote(getApplicationContext(), mLoadedNote.getmDateTime() + Utilities.FILE_EXTENSION);
                              Toast.makeText(getApplicationContext()
                                      , mEtTitle.getText().toString() +"is Deleted",Toast.LENGTH_SHORT ).show();
                              finish();
                             }
                         })
                 .setNegativeButton("no", null)
                 .setCancelable(false);

         dialog.show();
     }
    }
}
