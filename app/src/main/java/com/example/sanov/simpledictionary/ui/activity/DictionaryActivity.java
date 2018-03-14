package com.example.sanov.simpledictionary.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sanov.simpledictionary.R;
import com.example.sanov.simpledictionary.adapter.EngToIndAdapter;
import com.example.sanov.simpledictionary.adapter.IndToEngAdapter;
import com.example.sanov.simpledictionary.db.helper.EngToIndHelper;
import com.example.sanov.simpledictionary.db.helper.IndToEngHelper;
import com.example.sanov.simpledictionary.model.EngToIndModel;
import com.example.sanov.simpledictionary.model.IndToEngModel;

import java.util.ArrayList;
//snvl
public class DictionaryActivity extends AppCompatActivity implements EngToIndAdapter.WordListener, IndToEngAdapter.WordListener {

    private boolean flags = false;
    EditText edtSearch;
    TextView tvMeaning;
    RecyclerView recyclerView;
    EngToIndAdapter engToIndAdapter;
    IndToEngAdapter indToEngAdapter;
    EngToIndHelper engToIndHelper;
    IndToEngHelper indToEngHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        setTitle(getResources().getString(R.string.eng_to_ind));
        edtSearch = findViewById(R.id.edt_search);
        tvMeaning = findViewById(R.id.tv_meaning);
        recyclerView = findViewById(R.id.rv_word);

        engToIndAdapter = new EngToIndAdapter(this);
        engToIndHelper = new EngToIndHelper(this);

        indToEngAdapter = new IndToEngAdapter(this);
        indToEngHelper = new IndToEngHelper(this);

        engToIndAdapter.setOnItemClickListener(this);
        indToEngAdapter.setOnclickListener(this);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                tvMeaning.setText("");
                if (!flags) {

                    recyclerView.setAdapter(engToIndAdapter);

                    if (TextUtils.isEmpty(charSequence.toString().trim())) {
                        engToIndAdapter.ClearList();
                    } else {
                        engToIndHelper.open();
                        ArrayList<EngToIndModel> engToIndModels = engToIndHelper.getDataByKeywords(charSequence.toString());
                        engToIndHelper.close();
                        engToIndAdapter.addItem(engToIndModels);
                    }
                } else {

                    recyclerView.setAdapter(indToEngAdapter);

                    if (TextUtils.isEmpty(charSequence.toString().trim())) {
                        indToEngAdapter.ClearList();
                    } else {
                        indToEngHelper.open();
                        ArrayList<IndToEngModel> indToEngModels = indToEngHelper.getByKatakunci(charSequence.toString());
                        indToEngHelper.close();
                        indToEngAdapter.addItem(indToEngModels);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.swap) {
            if (flags) {
                flags = false;
                setTitle(getResources().getString(R.string.eng_to_ind));
                ResetField();
            } else {
                ResetField();
                flags = true;
                setTitle(getResources().getString(R.string.ind_to_eng));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickListener(String text) {
        tvMeaning.setText(text);
    }

    private void ResetField() {
        edtSearch.setText("");
        tvMeaning.setText("");
    }
}
