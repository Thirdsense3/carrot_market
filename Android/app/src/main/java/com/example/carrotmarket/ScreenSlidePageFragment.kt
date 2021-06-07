package com.example.carrotmarket

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_screen_slide_page.*

/**
 *
 * https://android-dev.tistory.com/12 참고
 * */

class ScreenSlidePageFragment(private val image : Int) : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_slide_image.setImageResource(image)
    }
}