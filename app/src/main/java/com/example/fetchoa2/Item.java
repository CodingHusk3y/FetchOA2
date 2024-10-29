package com.example.fetchoa2;

public class Item {
    private final int id;
    private final int listId;
    private final String name;

    public Item(int id, int listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    // Check if the item is valid for display
    public boolean isValid() {
        return name != null && !name.isEmpty();
    }
}
