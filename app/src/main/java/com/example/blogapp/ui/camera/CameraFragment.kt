package com.example.blogapp.ui.camera

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blogapp.R


class CameraFragment : Fragment(R.layout.fragment_camera) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    }

}
