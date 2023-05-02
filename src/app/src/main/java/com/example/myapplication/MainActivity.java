package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String URL_REGEX = "^((http[s]?|ftp)://)?(www\\.)?[a-zA-Z0-9]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)*(:[0-9]+)?(/[\\w#!:.?+=&%@\\-/]+)?/?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

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
            Matcher matcher = URL_PATTERN.matcher(text);
            if (!matcher.matches()){
                Toast.makeText(getApplicationContext(), "URL is invalid", Toast.LENGTH_LONG).show();
            } else if (url_array.contains(text)){
                Toast.makeText(getApplicationContext(), "URL is reduplicated", Toast.LENGTH_LONG).show();
            } else {
                url_array.add(text);
            }
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