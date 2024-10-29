package com.example.fetchoa2; // Change this to your actual package name

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        fetchItems();
    }

    private void fetchItems() {
        String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

        JsonArrayRequest jsonArrayRequest = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    this::parseJson,
                    error -> Log.e("MainActivity", "Error: " + error.getMessage())
            );
        }

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void parseJson(JSONArray jsonArray) {
        Map<Integer, List<Item>> groupedItems = new HashMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int listId = jsonObject.getInt("listId");
                String name = jsonObject.optString("name", null);

                // Filter out items with a null or blank name
                if (!name.trim().isEmpty()) {
                    Item item = new Item(jsonObject.getInt("id"), listId, name);

                    // Group items by listId
                    if (!groupedItems.containsKey(listId)) {
                        groupedItems.put(listId, new ArrayList<>());
                    }
                    Objects.requireNonNull(groupedItems.get(listId)).add(item);
                }
            } catch (JSONException e) {
                Log.e("MainActivity", "JSON Parsing error: " + e.getMessage());
            }
        }

        // Convert the grouped items to a sorted list
        for (Map.Entry<Integer, List<Item>> entry : groupedItems.entrySet()) {
            List<Item> items = entry.getValue();
            Collections.sort(items, Comparator.comparing(Item::getName));
            itemList.addAll(items);
        }

        // Sort the entire list by listId
        Collections.sort(itemList, Comparator.comparingInt(Item::getListId));

        // Set up the adapter
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);
    }
}
