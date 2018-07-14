package com.example.hosse.mytaskmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListFragment extends Fragment {

    private int mode;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mode = getArguments().getInt(ListActivity.TAB_MODE_KEY);
        Log.e("TabNumber", String.valueOf(mode));
        noteAdapter = new NoteAdapter(getActivity(),mode);
        recyclerView.setAdapter(noteAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }


}
