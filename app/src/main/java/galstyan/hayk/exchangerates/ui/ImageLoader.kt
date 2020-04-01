package galstyan.hayk.exchangerates.ui

import android.widget.ImageView
import androidx.annotation.MainThread


/**
 * Implementations may not use the UI thread
 */
@MainThread
interface ImageLoader {

    /**
     * @param url Implementations should fail silently when this is null
     */
    @MainThread
    fun load(url: String?): ImageLoader


    @MainThread
    fun into(imageView: ImageView)
}