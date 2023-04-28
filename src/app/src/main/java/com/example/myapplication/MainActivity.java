package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up the ArrayAdapter
        List<String> url_array = new ArrayList<>();
        url_array.add("https://www.anu.edu.au/");
        url_array.add("https://www.google.com/");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                url_array);
        ListView listView = (ListView) findViewById(R.id.ListViewId);
        listView.setAdapter(adapter);

        // Adding URLs to the ListView
        Button button = (Button) findViewById(R.id.addButtonId);
        EditText editText = (EditText) findViewById(R.id.EditTextId);
        button.setOnClickListener(v -> {
            String text = editText.getText().toString();
            url_array.add(text);
            editText.setText("");
            adapter.notifyDataSetChanged();
        });

        // Listening for clicks on the ListView
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), ActivityWeb.class);
            intent.putExtra("URL", url_array.get(i));
            startActivity(intent);
        });

    }
}