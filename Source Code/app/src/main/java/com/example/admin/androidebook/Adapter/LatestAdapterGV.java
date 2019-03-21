package com.example.admin.androidebook.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.androidebook.Activity.SCDetail;
import com.example.admin.androidebook.Item.SubCategoryList;
import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Method;
import com.example.admin.androidebook.Util.PopUpAds;
import com.github.ornolfr.ratingview.RatingView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 01-01-2018.
 */

public class LatestAdapterGV extends RecyclerView.Adapter<LatestAdapterGV.ViewHolder> {

    private Activity activity;
    private List<SubCategoryList> subCategoryLists;
    private Method method;
    private int columnWidth;
    private String type;

    public LatestAdapterGV(Activity activity, List<SubCategoryList> subCategoryLists,String type) {
        this.activity = activity;
        this.subCategoryLists = subCategoryLists;
        method = new Method(activity);
        columnWidth = method.getScreenWidth();
        Resources r = activity.getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, r.getDisplayMetrics());
        columnWidth = (int) ((method.getScreenWidth() - ((4 + 1) * padding)));
        this.type=type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.latest_gridview_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.cardView.setLayoutParams(new CardView.LayoutParams(columnWidth / 3, columnWidth / 2));
        holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth / 3, columnWidth / 2));
        holder.view.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth / 3, columnWidth / 2));
        holder.viewImage.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth / 3, columnWidth / 2));

        holder.textView_Name.setText(subCategoryLists.get(position).getBook_title());
        holder.textView_author.setText(subCategoryLists.get(position).getAuthor_name());
        holder.textView_ratingCount.setText(subCategoryLists.get(position).getTotal_rate());
        holder.ratingBar.setRating(Float.parseFloat(subCategoryLists.get(position).getRate_avg()));

        Picasso.with(activity).load(subCategoryLists.get(position).getBook_cover_img())
                .placeholder(R.drawable.placeholder_portable)
                .into(holder.imageView);

        Animation animation = AnimationUtils.loadAnimation(activity,
                R.anim.up_from_bottom_left);
        holder.itemView.startAnimation(animation);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpAds.ShowInterstitialAds(activity);
                activity.startActivity(new Intent(activity, SCDetail.class)
                        .putExtra("bookId", subCategoryLists.get(position).getId())
                        .putExtra("position", position)
                        .putExtra("type", type));
            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategoryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView;
        private View view, viewImage;
        private TextView textView_Name, textView_author, textView_ratingCount;
        private RatingView ratingBar;
        private CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (RoundedImageView) itemView.findViewById(R.id.imageView_latest_gridview_adapter);
            cardView = (CardView) itemView.findViewById(R.id.cardView_latest_gridview_adapter);
            view = (View) itemView.findViewById(R.id.view_latest_gridView_adapter);
            viewImage = (View) itemView.findViewById(R.id.viewImage_latest_gridView_adapter);
            textView_Name = (TextView) itemView.findViewById(R.id.textView_title_latest_gridview_adapter);
            textView_author = (TextView) itemView.findViewById(R.id.textView_author_latest_gridview_adapter);
            ratingBar = (RatingView) itemView.findViewById(R.id.ratingBar_latest_gridview_adapter);
            textView_ratingCount = (TextView) itemView.findViewById(R.id.textView_ratingCount_latest_gridview_adapter);

        }
    }

}
