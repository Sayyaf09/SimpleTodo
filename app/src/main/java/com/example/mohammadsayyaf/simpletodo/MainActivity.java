package com.example.mohammadsayyaf.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> itemsArray;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    Button addItemBtn;
    static final int PICK_CONTACT_REQUEST = 1;
    int itemPosition;
    String reEditedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("launch","inside onCreate of Main Activity");
        readItems();
        addItemBtn=(Button) findViewById(R.id.addItemButton);
        lvItems=(ListView) findViewById(R.id.listView);
        //itemsArray=new ArrayList<String>();
        itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemsArray);
        lvItems.setAdapter(itemsAdapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Clicked item is--"+itemsArray.get(position),Toast.LENGTH_SHORT).show();
                String pos=itemsArray.get(position);
                Log.d("test",pos);
                Log.d("test","index value of list"+itemsArray.indexOf(pos));
                itemPosition=itemsArray.indexOf(pos);
                Intent intent=new Intent(MainActivity.this,EditList.class);
                intent.putExtra("clickedPos",pos);
                startActivityForResult(intent,PICK_CONTACT_REQUEST);
            }
        });
        setupListViewListener();



    }

    private void setupListViewListener() {
        Log.e("launch","inside setupListViewListener");


        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemsArray.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return false;
            }
        });
    }

    public void addItems(View view) {
        Log.e("launch","inside addItems");
        EditText itemsEdit=(EditText) findViewById(R.id.item_editText);
        String items=itemsEdit.getText().toString();
        if(TextUtils.isEmpty(items))
        {
            itemsEdit.setError("The Field cannot be left empty");
        }
        else {
            itemsArray.add(items);
            itemsEdit.setText("");
            writeItems();
        }

    }
    private void readItems(){
        Log.e("launch","inside readItems");
        File filesDir=getFilesDir();
        File todoFile=new File(filesDir,"todo.txt");
        try {
            itemsArray=new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            itemsArray=new ArrayList<String>();
            e.printStackTrace();
        }
    }
    private void writeItems(){
        Log.e("launch","inside writeItems");

        File filesDir=getFilesDir();
        File todoFile=new File(filesDir,"todo.txt");
        try {
            FileUtils.writeLines(todoFile,itemsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("launch","inside onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode == PICK_CONTACT_REQUEST)
        {
            reEditedText=data.getStringExtra("editedText");
            Log.d("Test","reEditedText"+reEditedText);
            itemsArray.set(itemPosition,reEditedText);
            writeItems();
            itemsAdapter.notifyDataSetChanged();

        }
    }
}
