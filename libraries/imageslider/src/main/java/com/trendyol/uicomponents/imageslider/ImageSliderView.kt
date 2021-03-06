package com.trendyol.uicomponents.imageslider

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.trendyol.uicomponents.imageslider.databinding.ViewImageSliderBinding
import java.lang.ref.WeakReference

class ImageSliderView : FrameLayout, ImagesPagerAdapter.SliderAdapterItemClickListener, ImageSlider {

    private var binding: ViewImageSliderBinding = inflate(R.layout.view_image_slider)

    private var hostActivityWeakReference: WeakReference<Activity>

    private var imageSliderViewListener: ImageSliderViewListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    interface ImageSliderViewListener {

        fun onImageSliderItemClicked(imageUrlList: List<String>, position: Int)
    }

    init {
        hostActivityWeakReference = WeakReference<Activity>(null)
    }

    override fun setImageSliderViewListener(imageSliderViewListener: ImageSliderViewListener) {
        this.imageSliderViewListener = imageSliderViewListener
    }

    override fun setActivityInstance(activity: AppCompatActivity) {
        hostActivityWeakReference = WeakReference(activity)
    }

    fun setViewState(imageSliderViewState: ImageSliderViewState?) {
        imageSliderViewState?.let {
            binding.viewState = it
            binding.executePendingBindings()

            setImages(imageSliderViewState)
        }
    }

    private fun setImages(imageSliderViewState: ImageSliderViewState?) {
        imageSliderViewState?.imageList?.let { images ->
            val pagerAdapter =
                ImagesPagerAdapter(hostActivityWeakReference, this).apply {
                    addToImageList(images)
                    imageHeight = imageSliderViewState.imageHeight
                    isImageDynamic = imageSliderViewState.isImageDynamic == true
                }
            binding.viewPagerImageSlider.adapter = pagerAdapter
            binding.indicatorImageSlider.setViewPager(binding.viewPagerImageSlider)
        }
    }



    override fun onImageItemClicked(imageUrlList: List<String>, position: Int) {
        imageSliderViewListener?.onImageSliderItemClicked(imageUrlList, position)
    }
}
