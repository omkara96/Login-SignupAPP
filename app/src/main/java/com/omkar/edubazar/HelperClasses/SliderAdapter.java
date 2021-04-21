package com.omkar.edubazar.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.omkar.edubazar.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[]= {
            R.drawable.ic_splash_screen_bg,
            R.drawable.ic_splash_screen_bg,
            R.drawable.ic_next_btn,
            R.drawable.ic_splash_screen_bg
    };

    int headings[] ={
            R.string.firest_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title,
            R.string.firest_slide_title
    };

    int descriptions[] ={
      R.string.first_slide_Description,
      R.string.second_slide_Description,
      R.string.third_slide_Description,
      R.string.fourth_slide_Description
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==(ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

       layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
       View view = layoutInflater.inflate(R.layout.slides_layout,container,false);

        ImageView imageView = view.findViewById(R.id.siderImage);
        TextView heading = view.findViewById(R.id.sliderHeading);
        TextView desc = view.findViewById(R.id.sliderDescription);

        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        desc.setText(descriptions[position]);

        container.addView(view);

       return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout)object);
       // super.destroyItem(container, position, object);
    }
}
