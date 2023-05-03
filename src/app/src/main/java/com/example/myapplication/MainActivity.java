package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String URL_REGEX = "^((https?|ftp)://)?(www\\.)?[a-zA-Z0-9]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)*(:[0-9]+)?(/[\\w#!:.?+=&%@\\-/]+)?/?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up the ArrayAdapter
        List<String> url_array = new ArrayList<>();
//        url_array.add("https://www.anu.edu.au/");
//        url_array.add("https://www.google.com/");

            // Get url list for specific user
        User user = (User) getIntent().getExtras().getSerializable("USER", User.class);
        String id = user.getId();
           // load data
        Gson gson = new Gson();
        Type CUS_MAP_TYPE = new TypeToken< HashMap<String,List<String>> >() {}.getType();
//        Type CUS_MAP_TYPE = TypeToken.getParameterized(HashMap.class, ArrayList.class, String.class).getType();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.data)));
        HashMap<String, List<String>> url = gson.fromJson(jsonReader, CUS_MAP_TYPE);
        List<String> userURL = url.get(id);
        if (userURL != null){
            url_array.addAll(userURL);
        }

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
            Intent intent = new Intent(getApplicationContext(), WebActivity.class);
            intent.putExtra("URL", url_array.get(i));
            startActivity(intent);
        });

    }
}