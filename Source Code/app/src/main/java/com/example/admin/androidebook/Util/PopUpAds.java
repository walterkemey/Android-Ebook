package com.example.admin.androidebook.Util;

import android.app.Activity;

import com.example.admin.androidebook.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class PopUpAds {

    public static void ShowInterstitialAds(Activity activity) {

        Constant_Api.AD_COUNT += 1;
        if (Constant_Api.AD_COUNT == Constant_Api.AD_COUNT_SHOW) {
            final InterstitialAd mInterstitial = new InterstitialAd(activity);
            mInterstitial.setAdUnitId(activity.getResources().getString(R.string.admob_interstitial_id));
            mInterstitial.loadAd(new AdRequest.Builder().build());
            mInterstitial.show();
            Constant_Api.AD_COUNT = 0;
            if (!mInterstitial.isLoaded()) {
                AdRequest adRequest1 = new AdRequest.Builder()
                        .build();
                mInterstitial.loadAd(adRequest1);
            }
            mInterstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mInterstitial.show();
                }
            });
        }
    }
}
