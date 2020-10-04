package com.example.dictionary.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dictionary.R;
import com.example.dictionary.controller.fragment.ListWordsFragment;

import java.util.List;

public class ListWordsActivity extends SingleFragmentActivity {
    public static Intent newIntent (Context context){
        Intent intent = new Intent(context, ListWordsActivity.class);
        return intent;
    }




    @Override
    public Fragment createFragment() {
        ListWordsFragment listWordsFragment =ListWordsFragment.newInstance();
        return listWordsFragment;
    }
}