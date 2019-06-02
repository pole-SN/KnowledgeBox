package com.kotlin.mypractice.knowledgebox.steptwo.scenes.imageviewer

import android.os.Bundle
import android.view.*
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.kotlin.mypractice.knowledgebox.R
import com.kotlin.mypractice.knowledgebox.root.RootFragment
import kotlinx.android.synthetic.main.fragment_image_viewer.*

class ImageViewerFragment : Fragment(), RootFragment, View.OnTouchListener {
    private lateinit var mScaleGestureDetector: ScaleGestureDetector
    private lateinit var mPanGestureDetector: GestureDetectorCompat

    private var mScaleFactor = 1.0f

    private var mScaleVariableImageWidth = 0f
    private var mScaleVariableImageHeight = 0f
    private var mFixedDefaultImageWidth = 0f
    private var mFixedDefaultImageHeight = 0f
    private var mImageViewWidth = 0f
    private var mImageViewHeight = 0f

    private var mPointerId = 0
    private var mMoveX = 0f
    private var mMoveY = 0f
    private var mRawX = 0f
    private var mRawY = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mScaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
        mPanGestureDetector = GestureDetectorCompat(context, PanListener())

        return inflater.inflate(
            R.layout.fragment_image_viewer,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnTouchListener(this)

        image_view.setImageDrawable(context?.getDrawable(R.drawable.cat))
        val viewTreeObserver = image_view.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    image_view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val imageAspectRatio = image_view.drawable.intrinsicHeight.toFloat() /
                            image_view.drawable.intrinsicWidth.toFloat()
                    val viewAspectRatio = image_view.height.toFloat() / image_view.width.toFloat()

                    mScaleVariableImageWidth = if (imageAspectRatio < viewAspectRatio) {
                        image_view.width.toFloat()
                    } else {
                        image_view.height.toFloat() / imageAspectRatio
                    }

                    mScaleVariableImageHeight = if (imageAspectRatio < viewAspectRatio) {
                        image_view.width.toFloat() * imageAspectRatio
                    } else {
                        image_view.height.toFloat()
                    }

                    mFixedDefaultImageWidth = mScaleVariableImageWidth
                    mFixedDefaultImageHeight = mScaleVariableImageHeight

                    mImageViewWidth = image_view.width.toFloat()
                    mImageViewHeight = image_view.height.toFloat()
                }
            })
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            mRawX = 0F
            mRawY = 0F
        }
        mScaleGestureDetector.onTouchEvent(event)
        mPanGestureDetector.onTouchEvent(event)

        return true
    }

    private inner class PanListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent,
            distanceX: Float, distanceY: Float
        ): Boolean {
            // distanceX and distanceY obtained by Fragment has a defect.
            // So I used RawXY to recalculate.

            //　If Pointer is (finger point id) changes, initialize the local data.
            if (e2.getPointerId(0) != mPointerId) {
                mPointerId = e2.getPointerId(0)
                mRawX = 0F
                mRawY = 0F
            }

            val translationX =
                if ((mRawX == 0F) && (mRawY == 0F)) mMoveX else mMoveX - (mRawX - e2.rawX)
            val translationY =
                if ((mRawX == 0F) && (mRawY == 0F)) mMoveY else mMoveY - (mRawY - e2.rawY)

            adjustTranslation(translationX, translationY)

            mRawX = e2.rawX
            mRawY = e2.rawY

            return true
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            mScaleFactor *= mScaleGestureDetector.scaleFactor
            mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 4.0f))
            image_view.scaleX = mScaleFactor
            image_view.scaleY = mScaleFactor
            mScaleVariableImageWidth = mFixedDefaultImageWidth * mScaleFactor
            mScaleVariableImageHeight = mFixedDefaultImageWidth * mScaleFactor

            adjustTranslation(mMoveX, mMoveY)

            return true
        }
    }

    private fun adjustTranslation(translationX: Float, translationY: Float) {
        val translationXMargin = Math.abs((mScaleVariableImageWidth - mImageViewWidth) / 2)
        val translationYMargin = Math.abs((mScaleVariableImageHeight - mImageViewHeight) / 2)

        if (translationX < 0) {
            mMoveX = Math.max(translationX, -translationXMargin)
        } else {
            mMoveX = Math.min(translationX, translationXMargin)
        }

        if (translationY < 0) {
            mMoveY = Math.max(translationY, -translationYMargin)
        } else {
            mMoveY = Math.min(translationY, translationYMargin)
        }

        image_view.translationX = mMoveX
        image_view.translationY = mMoveY
    }
}