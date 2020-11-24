package com.cvmaker.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import static com.cvmaker.utils.LogUtils.LOGE;


/**
 * Created by aya mohamed on 11/12/19.
 */

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        if (url != null) {
            LOGE(url);
            Glide.with(context).load(url).into(imageView);
        }
    }


}
