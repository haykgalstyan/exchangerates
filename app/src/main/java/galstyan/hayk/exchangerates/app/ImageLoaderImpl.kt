package galstyan.hayk.exchangerates.app

import android.widget.ImageView
import com.squareup.picasso.Picasso
import galstyan.hayk.exchangerates.ui.ImageLoader
import java.util.*


class ImageLoaderImpl : ImageLoader {

    private val mPicasso = Picasso.get()

    private var mUrl: String? = null


    override fun load(url: String?): ImageLoader {
        mUrl = url
        return this
    }


    override fun into(imageView: ImageView) {
        if (mUrl == null || mUrl!!.isEmpty()) return
        mPicasso.load(mUrl).into(Objects.requireNonNull(imageView))
        mUrl = null
    }
}