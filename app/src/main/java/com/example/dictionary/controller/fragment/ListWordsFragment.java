package com.example.dictionary.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dictionary.R;

public class ListWordsFragment extends Fragment {
    private RecyclerView mRecyclerView;



    public ListWordsFragment() {

    }


    public static ListWordsFragment newInstance() {
        ListWordsFragment fragment = new ListWordsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_words, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycle_view_words);
    }
}