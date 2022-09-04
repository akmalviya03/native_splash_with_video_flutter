package com.example.native_splash_with_video_flutter

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Half.toFloat
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.postDelayed
import io.flutter.embedding.android.FlutterActivity

class MainActivity: FlutterActivity() {
     private lateinit var videoView : VideoView
     private lateinit var splashScreen: SplashScreen
     private var videoDuration : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This activity will be handling the splash screen transition.
         splashScreen = installSplashScreen()

//        // The splash screen goes edge to edge, so for a smooth transition to our app, also
//        // want to draw edge to edge.
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
//        insetsController.isAppearanceLightNavigationBars = true
//        insetsController.isAppearanceLightStatusBars = true

        // The content view needs to be set before calling setOnExitAnimationListener
        // to ensure that the SplashScreenView is attached to the right view root.
        val rootLayout = findViewById<FrameLayout>(android.R.id.content)
        View.inflate(this, R.layout.activity_main, rootLayout)

        //Giving insets to the constraint layout
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraint_layout)) { view, windowInsets ->
//            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
//            view.setPadding(insets.left, insets.top, insets.right, insets.bottom)
//            windowInsets.inset(insets)
//        }

        videoView= findViewById(R.id.videoView)
        videoView.setVideoPath("android.resource://"+packageName+"/"+ R.raw.test_video)
        videoView.setMediaController(null)
        videoDuration = videoView.duration
        videoView.start()

        splashScreen.setOnExitAnimationListener{
                splashScreenViewProvider->
            println("Hello World Not Working")
            // Create your custom animation.
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenViewProvider.view,
                View.TRANSLATION_Y,
                0f,
                -splashScreenViewProvider.view.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 500L

            // Call SplashScreenView.remove at the end of your custom animation.
            slideUp.doOnEnd { splashScreenViewProvider.remove() }

            // Run your animation.
            slideUp.start()
        }
    }


}
