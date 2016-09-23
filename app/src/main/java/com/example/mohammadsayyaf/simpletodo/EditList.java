package com.example.mohammadsayyaf.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * Created by mohammad.sayyaf on 22-09-2016.
 */
public class EditList extends AppCompatActivity {
    TextView editTextView;
    EditText editItemText;
    Button saveButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_list);
        Log.e("launch","inside onCreate of EditList Class");
        Intent getIntent=getIntent();
        String clickedItem=getIntent.getStringExtra("clickedPos").toString();
        Log.d("edit_Test",clickedItem);
        editTextView=(TextView) findViewById(R.id.editTextView);
        editItemText=(EditText) findViewById(R.id.editEditText);
        saveButton=(Button) findViewById(R.id.saveButton);
        //editItemText.setText(clickedItem,TextView.BufferType.EDITABLE);
        editItemText.setText(clickedItem);
        editItemText.setSelection(editItemText.getText().length());
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWrite();
            }
        });

    }

    private void editWrite() {
        String newEditedText=editItemText.getText().toString();
        Log.d("test","edited text is*****"+newEditedText);
        Intent intent=new Intent(EditList.this,MainActivity.class);
        intent.putExtra("editedText",newEditedText);
        setResult(RESULT_OK,intent);
        finish();
    }

}
