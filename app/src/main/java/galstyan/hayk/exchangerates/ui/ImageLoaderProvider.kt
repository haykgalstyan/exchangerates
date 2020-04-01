package galstyan.hayk.exchangerates.ui

import galstyan.hayk.exchangerates.app.ImageLoaderImpl


/**
 * Hiding implementation of [ImageLoader] from users so its easy to change later if needed
 */
class ImageLoaderProvider {

    val imageLoader: ImageLoader get() = _imageLoader
    private val _imageLoader: ImageLoaderImpl = ImageLoaderImpl()

}