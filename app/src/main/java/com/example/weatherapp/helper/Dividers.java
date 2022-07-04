package com.example.weatherapp.helper;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Dividers {
    //Add dividers between recyclerView items
    public static void addDividerLines(RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
