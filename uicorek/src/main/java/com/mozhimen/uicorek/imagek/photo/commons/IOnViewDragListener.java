package com.mozhimen.uicorek.imagek.photo.commons;

/**
 * Interface definition for a callback to be invoked when the photo is experiencing a drag event
 */
public interface IOnViewDragListener {

    /**
     * Callback for when the photo is experiencing a drag event. This cannot be invoked when the
     * user is scaling.
     *
     * @param dx The change of the coordinates in the x-direction
     * @param dy The change of the coordinates in the y-direction
     */
    void onDrag(float dx, float dy);
}
