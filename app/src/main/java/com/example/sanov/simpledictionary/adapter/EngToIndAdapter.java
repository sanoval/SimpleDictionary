package com.example.sanov.simpledictionary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sanov.simpledictionary.R;
import com.example.sanov.simpledictionary.model.EngToIndModel;

import java.util.ArrayList;

/**
 * Created by sanov on 3/13/2018.
 */

public class EngToIndAdapter extends RecyclerView.Adapter<EngToIndAdapter.EngToIndHolder> {
    private ArrayList<EngToIndModel> wordData = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    public WordListener wordListener;

    public void setOnItemClickListener(WordListener wordListener) {
        this.wordListener = wordListener;
    }

    public EngToIndAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public EngToIndHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new EngToIndHolder(view);
    }

    public void addItem(ArrayList<EngToIndModel> wordData) {
        this.wordData = wordData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(EngToIndHolder holder, final int position) {
        holder.tvWord.setText(wordData.get(position).getKeywords());
        holder.tvWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordListener.onItemClickListener(wordData.get(position).getBody());
            }
        });

    }

    @Override
    public int getItemCount() {
        return wordData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class EngToIndHolder extends RecyclerView.ViewHolder {

        private TextView tvWord;

        public EngToIndHolder(View view) {
            super(view);

            tvWord = view.findViewById(R.id.tv_word);

        }
    }

    public void ClearList() {
       wordData.clear();
       notifyDataSetChanged();
    }

    public interface WordListener {
        void onItemClickListener(String text);
    }
}
