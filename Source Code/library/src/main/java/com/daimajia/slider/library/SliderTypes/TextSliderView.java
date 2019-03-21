package com.daimajia.slider.library.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.R;
import com.daimajia.slider.library.Width;
import com.squareup.picasso.Picasso;

/**
 * This is a slider with a description TextView.
 */
public class TextSliderView extends BaseSliderView {


    public TextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text, null);

        ImageView imageView_background = (ImageView) v.findViewById(R.id.imageView_background_render_type_text);
        ImageView imageView_logo = (ImageView) v.findViewById(R.id.imageView_logo_render_type_text);
        TextView textView_bookName = (TextView) v.findViewById(R.id.textView_book_name_render_type_text);
        TextView textView_author = (TextView) v.findViewById(R.id.textView_author_name_render_type_text);
        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar_render_type_text);
        TextView textView_ratingView = (TextView) v.findViewById(R.id.textView_ratingView_render_type_text);

        ratingBar.setClickable(false);

        textView_bookName.setText(getName());
        textView_author.setText(getSub_name());
        ratingBar.setRating(Float.parseFloat(getRating()));
        textView_ratingView.setText(getRatingView());
        bindEventAndShow(v, imageView_background);

        Picasso.with(getContext()).load(getLogo()).placeholder(R.drawable.back_ground).into(imageView_logo);

        return v;
    }
}
