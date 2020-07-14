package com.assignmet;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private LayoutInflater inflater;

    Activity mContext;
    ClickListener clickListener;
    List<User> user_list;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_EMPTY = 1;
    Boolean isFromTransaction=false;
    List<String> colorList = new ArrayList<>();
    public UserListAdapter(Activity context, List<User> listData ){

        inflater = LayoutInflater.from(context);
        this.user_list = listData;
        this.mContext = context;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == VIEW_TYPE_ITEM){
            View view = inflater.inflate(R.layout.recipient_list_item03,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }else  if(viewType == VIEW_TYPE_EMPTY){
            View view = inflater.inflate(R.layout.empty_list_item,parent,false);
            EmptyViewHolder holder = new EmptyViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof MyViewHolder){
            MyViewHolder ItemViewHolder = (MyViewHolder) holder;

            ItemViewHolder.user_name_tv.setText(user_list.get(position).getName().toUpperCase());

        }else {
                EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return user_list.get(position) == null ? VIEW_TYPE_EMPTY : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView user_name_tv;
        ImageView user_image;
        LinearLayout list_item;


        public MyViewHolder(View itemView) {
            super(itemView);

            user_name_tv =  itemView.findViewById(R.id.user_name);
            list_item = (LinearLayout) itemView.findViewById(R.id.list_item);
            list_item.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(clickListener!=null){
                clickListener.itemClicked(v,getAdapterPosition());
            }
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public EmptyViewHolder(View itemView) {
            super(itemView);

        }


        @Override
        public void onClick(View v) {
            if(clickListener!=null){
                clickListener.itemClicked(v,getAdapterPosition());
            }
        }
    }
    public interface ClickListener{

        void itemClicked(View v, int position);
    }

}
