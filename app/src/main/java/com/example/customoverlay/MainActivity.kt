package com.example.customoverlay

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.icu.number.Scale
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_SELECTION = 1
    private lateinit var customImageOverlayView: CustomImageOverlayView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customImageOverlayView = findViewById(R.id.customImageOverlayView)
        val btnSelectImage = findViewById<Button>(R.id.btnSelectImage)
        val et_caption = findViewById<EditText>(R.id.et_caption)
        btnSelectImage.setOnClickListener {
            customImageOverlayView.setOverlayText(et_caption.text.toString())
            openGallery()
        }
        customImageOverlayView.setOverlayTextColor(Color.BLUE)
        customImageOverlayView.setOverlayTextSize(32f)
        customImageOverlayView.setOverlayTextPosition(300f, 200f)


    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_SELECTION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_SELECTION && resultCode == RESULT_OK && data != null){
            val imageUri: Uri? = data.data
            if (imageUri != null) {
                customImageOverlayView.setBackgroundImageUri(imageUri)
                customImageOverlayView.visibility = View.VISIBLE
            }

        }
    }

}