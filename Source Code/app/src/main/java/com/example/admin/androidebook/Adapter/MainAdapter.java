package com.example.admin.androidebook.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Method;
import com.squareup.picasso.Picasso;

/**
 * Created by admin on 01-01-2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Activity activity;
    private String[] name;
    private Integer[] image;
    private int row_index = -1;
    private Method method;

    public MainAdapter(Activity activity, String[] name, Integer[] image) {
        this.activity = activity;
        this.name = name;
        this.image = image;
        method = new Method(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.main_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Picasso.with(activity).load(image[position]).into(holder.imageView);
        holder.textView_Name.setText(name[position]);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();
            }
        });

        if (row_index == position) {
            holder.linearLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.draware_selected_background));
            holder.textView_Name.setTextColor(activity.getResources().getColor(R.color.drawar_text_select));
            holder.imageView.setColorFilter(activity.getResources().getColor(R.color.drawar_image_select));
        } else {
            holder.linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.drawar_layout_background));
            holder.textView_Name.setTextColor(activity.getResources().getColor(R.color.drawar_text));
            holder.imageView.setColorFilter(0);
        }

        if (position == 12) {
            if (!method.pref.getBoolean(method.pref_login, false)) {
                holder.textView_Name.setText(activity.getResources().getString(R.string.login));
            }
        }

    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView_Name;
        private LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView_main_adapter);
            textView_Name = (TextView) itemView.findViewById(R.id.textView_main_adapter);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout_main_adapter);

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

}
