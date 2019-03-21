package com.example.admin.androidebook.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.androidebook.Activity.FavouriteDetail;
import com.example.admin.androidebook.Item.ScdList;
import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.PopUpAds;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 01-01-2018.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private Activity activity;
    private List<ScdList> scdLists;
    private int lastPosition = -1;

    public FavouriteAdapter(Activity activity, List<ScdList> scdLists) {
        this.activity = activity;
        this.scdLists = scdLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.latest_listview_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.linearLayoutRating.setVisibility(View.GONE);
        holder.linearLayoutView.setVisibility(View.GONE);

        holder.textView_Name.setText(scdLists.get(position).getBook_title());
        holder.textView_author.setText(scdLists.get(position).getAuthor_name());
        holder.textView_ratingCount.setText(scdLists.get(position).getTotal_rate());
        holder.textView_viewCount.setText(scdLists.get(position).getBook_views());
        holder.textView_description.setText(Html.fromHtml(scdLists.get(position).getBook_description()));
        holder.ratingBar.setRating(Float.parseFloat(scdLists.get(position).getRate_avg()));

        Picasso.with(activity).load(scdLists.get(position).getBook_cover_img())
                .placeholder(R.drawable.placeholder_portable)
                .into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpAds.ShowInterstitialAds(activity);
                activity.startActivity(new Intent(activity, FavouriteDetail.class)
                        .putExtra("bookId", scdLists.get(position).getId())
                        .putExtra("position", position));
            }
        });

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

    }

    @Override
    public int getItemCount() {
        return scdLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayoutRating, linearLayoutView;
        private RoundedImageView imageView;
        private TextView textView_Name, textView_author, textView_ratingCount, textView_viewCount, textView_description;
        private RatingBar ratingBar;
        private RelativeLayout relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_latestListView_adapter);
            imageView = (RoundedImageView) itemView.findViewById(R.id.imageView_latestListView_adapter);
            textView_Name = (TextView) itemView.findViewById(R.id.textView_name_latestListView_adapter);
            textView_author = (TextView) itemView.findViewById(R.id.textView_author_latestListView_adapter);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar_latestListView_adapter);
            textView_ratingCount = (TextView) itemView.findViewById(R.id.textView_ratingCount_latestListView_adapter);
            textView_viewCount = (TextView) itemView.findViewById(R.id.textView_view_latestListView_adapter);
            textView_description = (TextView) itemView.findViewById(R.id.textView_description_latestListView_adapter);
            linearLayoutRating = (LinearLayout) itemView.findViewById(R.id.linearLayout_latest_listview_adapter);
            linearLayoutView = (LinearLayout) itemView.findViewById(R.id.linearLayout_view_latest_listview_adapter);

        }
    }

}
