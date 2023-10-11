package com.example.customoverlay

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import java.io.IOException
import java.io.InputStream

class CustomImageOverlayView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var backgroundImage: Bitmap? = null
    private var overlayText: String = ""
    private var overlayTextColor: Int = 0
    private var overlayTextSize: Float = 0f
    private var overlayTextX: Float = 0f
    private var overlayTextY: Float = 0f

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageOverlayView)
        val backgroundImageUri = typedArray.getString(R.styleable.CustomImageOverlayView_backgroundImageUri)
        overlayText = typedArray.getString(R.styleable.CustomImageOverlayView_overlayText) ?: ""
        overlayTextColor = typedArray.getColor(
            R.styleable.CustomImageOverlayView_overlayTextColor,
            Color.BLACK
        )
        overlayTextSize =
            typedArray.getDimension(R.styleable.CustomImageOverlayView_overlayTextSize, 24f)
        overlayTextX = typedArray.getDimension(R.styleable.CustomImageOverlayView_overlayTextX, 0f)
        overlayTextY = typedArray.getDimension(R.styleable.CustomImageOverlayView_overlayTextY, 0f)
        typedArray.recycle()

        // Load the background image from the URI
        if (!backgroundImageUri.isNullOrBlank()) {
            backgroundImage = loadBitmapFromUri(Uri.parse(backgroundImageUri))
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the background image
        backgroundImage?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }

        // Draw overlay text
        val textPaint = Paint()
        textPaint.color = overlayTextColor
        textPaint.textSize = overlayTextSize
        canvas.drawText(overlayText, overlayTextX, overlayTextY, textPaint)
    }

    fun setBackgroundImageUri(imageUri: Uri) {
        // Load the background image from the URI
        backgroundImage = loadBitmapFromUri(imageUri)
        invalidate()
    }

    fun setOverlayText(text: String) {
        overlayText = text
        invalidate()
    }

    fun setOverlayTextColor(color: Int) {
        overlayTextColor = color
        invalidate()
    }

    fun setOverlayTextSize(size: Float) {
        overlayTextSize = size
        invalidate()
    }

    fun setOverlayTextPosition(x: Float, y: Float) {
        overlayTextX = x
        overlayTextY = y
        invalidate()
    }

    private fun loadBitmapFromUri(uri: Uri): Bitmap? {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}