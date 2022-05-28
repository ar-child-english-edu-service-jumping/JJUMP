package com.jjump.java.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.jjump.R;
import com.jjump.java.HomeActivity;

import java.util.List;

// 자식 어답터
public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {

    private List<SubItem> subItemList;

    SubItemAdapter(List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_word, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, int i) {
        SubItem subItem = subItemList.get(i);
        subItemViewHolder.frontWord.setText(subItem.getFrontWord());
        subItemViewHolder.backWord.setText(subItem.getBackWord());
        if(HomeActivity.bookmarkFlag)
            subItemViewHolder.bookmark.setVisibility(View.VISIBLE);
        else
            subItemViewHolder.bookmark.setVisibility(View.INVISIBLE);

        subItemViewHolder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subItem.getFlag()) {
                    subItemViewHolder.bookmark.setImageResource(R.drawable.ic_bookmark);
                    subItem.setFlag(false);
                }else{
                    subItemViewHolder.bookmark.setImageResource(R.drawable.ic_bookmark_add);
                    subItem.setFlag(true);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView frontWord;
        TextView backWord;
        ImageButton bookmark;

        SubItemViewHolder(View itemView) {
            super(itemView);
            frontWord = itemView.findViewById(R.id.card_front);
            backWord = itemView.findViewById(R.id.card_back);
            bookmark = itemView.findViewById(R.id.bookmark);
        }
    }
}