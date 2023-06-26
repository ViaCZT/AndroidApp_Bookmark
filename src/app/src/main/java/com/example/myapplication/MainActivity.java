package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

        FirebaseApp.initializeApp(getBaseContext());

        // read data to url_array.
        List<String> url_array = new ArrayList<>();
//        url_array.add("https://www.anu.edu.au/");
//        url_array.add("https://www.google.com/");

            // Get url list for specific user
        User user = (User) getIntent().getExtras().getSerializable("USER", User.class);
        String id = user.getId();
//           //【1】load data from res.raw folder
//        int resourceID = R.raw.data;
//        HashMap<String, List<String>> data = readJSONFile(resourceID);
//        List<String> userURL = data.get(id);
//        if (userURL != null){
//            url_array.addAll(userURL);
//        }
//            //【2】interact with Firebase
//            // 1. store data.json to Firebase (Only once)
//        for (Map.Entry<String, List<String>> entry: data.entrySet()){
//            String userid = entry.getKey();
//            List<String> urls = entry.getValue();
//            storeFire(userid, urls);
//        }
            // 2. read data from Firebase


        // Setting up the ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                url_array);
        readFire(id, url_array, adapter);
        ListView listView = findViewById(R.id.ListViewId);
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

    /**
     * Read data from res.raw folder
     * @param resourceID: the id of the files in R.raw
     * @return: A HashMap. Its key is user's id, and its value is the url list of this user.
     */
    protected HashMap<String, List<String>> readJSONFile(int resourceID){
        Gson gson = new Gson();
        Type CUS_MAP_TYPE = new TypeToken< HashMap<String,List<String>> >() {}.getType();
//        Type CUS_MAP_TYPE = TypeToken.getParameterized(HashMap.class, ArrayList.class, String.class).getType();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(getResources().openRawResource(resourceID)));
        HashMap<String, List<String>> data = gson.fromJson(jsonReader, CUS_MAP_TYPE);
        return data;
    }

    protected void storeFire(String reference, List<String> urls){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //有child
        DatabaseReference databaseReference = firebaseDatabase.getReference("URLs");
        databaseReference.child(reference).setValue(urls);
        //无child
//        DatabaseReference databaseReference = firebaseDatabase.getReference(reference);
//        databaseReference.setValue(urls);
    }

    protected void readFire(String reference, List<String> url_list1, ArrayAdapter<String> adapter){
//        System.out.println("Start readFire");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("URLs").child(reference);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()){
                    String url = childSnapshot.getValue(String.class);
//                    System.out.println("url: "+url);
                    url_list1.add(url);
//                    System.out.println("url_list 1 "+url_list1);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Failed to retrieve data, error: " + error.toException());
            }
        });
//        System.out.println("url_list: "+url_list1);
    }

}