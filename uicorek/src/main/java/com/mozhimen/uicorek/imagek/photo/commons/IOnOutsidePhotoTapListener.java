package com.mozhimen.uicorek.imagek.photo.commons;

import android.widget.ImageView;

/**
 * Callback when the user tapped outside of the photo
 */
public interface IOnOutsidePhotoTapListener {

    /**
     * The outside of the photo has been tapped
     */
    void onOutsidePhotoTap(ImageView imageView);
}
