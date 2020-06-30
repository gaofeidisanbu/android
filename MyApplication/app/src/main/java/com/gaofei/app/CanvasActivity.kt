package com.gaofei.app

import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.animated.base.AbstractAnimatedDrawable
import com.facebook.imagepipeline.image.ImageInfo
import com.gaofei.app.R.layout
import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.LogUtils
import kotlinx.android.synthetic.main.act_canvas.*
import kotlinx.android.synthetic.main.layout_gif_item.view.*
import pl.droidsonroids.gif.GifDrawable


class CanvasActivity : BaseAct() {
    private val mData = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.act_canvas)
        initData()
//        viewPager.adapter = MyAdapter()
//        viewPager.offscreenPageLimit = 0
        val  controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse("https://isparta.github.io/compare-webp/image/gif_webp/webp/1.webp"))
                .setAutoPlayAnimations(true)
                .build()
//        draweeView.setController(controller)

        val gifDrawable = GifDrawable(resources, R.drawable.gif1)
        gifDrawalbe.setImageDrawable(gifDrawable)
    }

    private fun initData() {
        mData.add(R.drawable.webp1)
//        mData.add(R.drawable.gif2)
//        mData.add(R.drawable.gif3)

    }


  inner class MyAdapter: PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.layout_gif_item, container, false)
        view.setOnClickListener {
            mAnimatable?.start()

        }
//        loadGifImage(mData[position], view.gifImage)
        loadWebPImage(mData[position], view.gifImage)
        LogUtils.d("gifaa instantiateItem position = "+position)
        container.addView(view)
        return view
    }


      private fun loadImage(imageId: Int, view: ImageView) {
          Glide.with(view.context)
                  .load(imageId)
                  .diskCacheStrategy(DiskCacheStrategy.NONE)
                  .dontAnimate().into(view)

      }

      private fun loadGifImage(imageId: Int, view: ImageView) {
          Glide.with(view.context)
                  .asGif()
                  .load(imageId)
                  .diskCacheStrategy(DiskCacheStrategy.NONE)
//                  .listener(object : RequestListener<GifDrawable> {
//                      override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
//                          return false
//                      }
//
//                      override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                          LogUtils.d("onResourceReady ${resource}")
//                          return true
//                      }
//                  })
                  .into(view)

      }

      private var mAnimatable: AbstractAnimatedDrawable? = null

      private fun loadWebPImage(imageId: Int, view: SimpleDraweeView) {
//          view.setImageResource(imageId)

          val controllerListener = object :BaseControllerListener<ImageInfo>() {
              override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                  super.onFinalImageSet(id, imageInfo, animatable)
                  LogUtils.d("onFinalImageSet")
                  animatable?.let {
                      mAnimatable = it as AbstractAnimatedDrawable
                      it.start()
                  }
              }
          }

          val  controller = Fresco.newDraweeControllerBuilder()
//                  .setControllerListener(controllerListener)
                  .setUri(Uri.parse("https://isparta.github.io/compare-webp/image/gif_webp/webp/1.webp"))
//                  .setUri(Uri.parse("https://course.yangcong345.com/Fnt0gdDMm_Nj-2tmMsTUi31gXVp5"))
                  .setAutoPlayAnimations(true)
                  .build()
          view.setController(controller)
      }


    override fun isViewFromObject(view: View, object1: Any): Boolean {
        return view == object1
    }

    override fun getCount(): Int {
        return mData.size
    }

      override fun destroyItem(container: ViewGroup, position: Int, object1: Any) {
          container.removeView(object1 as View?)
          LogUtils.d("gifaa destroyItem position = "+position)
      }

}


}
