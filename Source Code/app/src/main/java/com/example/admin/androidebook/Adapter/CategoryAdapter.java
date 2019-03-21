package com.example.admin.androidebook.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.androidebook.Activity.SubCategory;
import com.example.admin.androidebook.Item.CategoryList;
import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.PopUpAds;

import java.util.List;

/**
 * Created by admin on 01-01-2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Activity activity;
    private List<CategoryList> categoryLists;
    private int lastPosition = -1;

    public CategoryAdapter(Activity activity, List<CategoryList> categoryLists) {
        this.activity = activity;
        this.categoryLists = categoryLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.category_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textView_Name.setText(categoryLists.get(position).getCategory_name());
        String totalBook = "(" + categoryLists.get(position).getTotal_books() + ") " + activity.getResources().getString(R.string.items_string_category_adapter);
        holder.textView_categoryCount.setText(totalBook);

        if (position > lastPosition) {
            if (position % 2 == 0) {
                Animation animation = AnimationUtils.loadAnimation(activity,
                        R.anim.up_from_bottom_right);
                holder.itemView.startAnimation(animation);
                lastPosition = position;
            } else {
                Animation animation = AnimationUtils.loadAnimation(activity,
                        R.anim.up_from_bottom_left);
                holder.itemView.startAnimation(animation);
                lastPosition = position;
            }
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpAds.ShowInterstitialAds(activity);
                activity.startActivity(new Intent(activity, SubCategory.class)
                        .putExtra("toolbarTitle", categoryLists.get(position).getCategory_name())
                        .putExtra("id", categoryLists.get(position).getCid())
                        .putExtra("type", "category"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativeLayout;
        private TextView textView_Name, textView_categoryCount;


        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativelayout_category_adapter);
            textView_Name = (TextView) itemView.findViewById(R.id.textViewName_category_adapter);
            textView_categoryCount = (TextView) itemView.findViewById(R.id.textView_categoryCount_category_adapter);

        }
    }
}
