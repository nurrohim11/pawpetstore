package com.asus.ecommerceapp.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.banner.BannerItem;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private ArrayList<BannerItem> images;
    private LayoutInflater inflater;
    private Context context;

    public SliderAdapter(Context context, ArrayList<BannerItem> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        BannerItem hero = images.get(position);
        View myImageLayout = inflater.inflate(R.layout.item_slide, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
//        myImage.setImageResource(images.get(position));
        Glide.with(context).load(hero.getGambar()).into(myImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}