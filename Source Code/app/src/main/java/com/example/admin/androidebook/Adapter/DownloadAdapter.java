package com.example.admin.androidebook.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.androidebook.Activity.PDFShow;
import com.example.admin.androidebook.DataBase.DatabaseHandler;
import com.example.admin.androidebook.Item.DownloadList;
import com.example.admin.androidebook.R;
import com.folioreader.model.HighLight;
import com.folioreader.util.FolioReader;
import com.folioreader.util.OnHighlightListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 01-01-2018.
 */

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    private Activity activity;
    private List<DownloadList> downloadLists;
    private int lastPosition = -1;
    private DatabaseHandler db;

    public DownloadAdapter(Activity activity, List<DownloadList> downloadLists) {
        this.activity = activity;
        this.downloadLists = downloadLists;
        db = new DatabaseHandler(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.download_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textView_Name.setText(downloadLists.get(position).getTitle());
        holder.textView_author.setText(downloadLists.get(position).getAuthor());

        Picasso.with(activity).load("file://" + downloadLists.get(position).getImage()).placeholder(R.drawable.placeholder_landscap).into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (downloadLists.get(position).getUrl().contains(".epub")) {
                    FolioReader folioReader = new FolioReader(activity);
                    folioReader.registerHighlightListener(new OnHighlightListener() {
                        @Override
                        public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {

                        }
                    });
                    folioReader.openBook(downloadLists.get(position).getUrl());
                } else {
                    activity.startActivity(new Intent(activity, PDFShow.class)
                            .putExtra("link", downloadLists.get(position).getUrl())
                            .putExtra("toolbarTitle", downloadLists.get(position).getTitle())
                            .putExtra("type", "file"));
                }

            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!db.checkIdPdf(downloadLists.get(position).getId())) {
                    db.deletePdf(downloadLists.get(position).getId());
                    downloadLists.remove(position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(activity, activity.getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                }
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
        return downloadLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativeLayout;
        private RoundedImageView imageView;
        private TextView textView_Name, textView_author;
        private Button button;


        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativelayout_download_adapter);
            button = (Button) itemView.findViewById(R.id.button_delete_adapter);
            imageView = (RoundedImageView) itemView.findViewById(R.id.imageView_download_adapter);
            textView_Name = (TextView) itemView.findViewById(R.id.textViewName_download_adapter);
            textView_author = (TextView) itemView.findViewById(R.id.textView_subTitle_download_adapter);

        }
    }
}
