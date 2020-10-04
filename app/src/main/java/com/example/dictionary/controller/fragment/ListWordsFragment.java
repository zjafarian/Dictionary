package com.example.dictionary.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dictionary.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListWordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListWordsFragment extends Fragment {



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
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_words, container, false);
        return view;
    }
}