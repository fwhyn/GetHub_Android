package com.fwhyn.app.gethub.feature.screen.main

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.fwhyn.app.gethub.BuildConfig
import com.fwhyn.app.gethub.common.helper.extension.emitEvent
import com.fwhyn.app.gethub.common.ui.base.BaseActivity
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.lib.baze.compose.helper.ActivityRetainedState
import com.fwhyn.lib.baze.compose.helper.rememberActivityState
import com.fwhyn.lib.baze.compose.model.ActivityResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val vm: MainViewModel by viewModels()
    private var activityResult: MutableSharedFlow<ActivityResult> = MutableSharedFlow()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("This App is ", "${if (BuildConfig.DEBUG) "debug" else "release"} version")

        val activityRetainedState = vm.activityRetainedState
        animateSplashScreen(activityRetainedState)

        enableEdgeToEdge()
        setContent {
            MyTheme {
                val activityState = rememberActivityState(
                    window = calculateWindowSizeClass(this),
                    activityResult = activityResult
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = activityState.notification) }
                ) { padding ->
                    MainScreen(
                        Modifier
                            .fillMaxSize()
                            .padding(padding),
                        activityState = activityState,
                        activityRetainedState = activityRetainedState,
                    )
                }
            }
        }

        finishWhenNeeded(activityRetainedState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, caller: ComponentCaller) {
        activityResult.emitEvent(
            lifecycleScope,
            ActivityResult(
                requestCode = requestCode,
                resultCode = resultCode,
                data = data
            )
        )
    }

    private fun animateSplashScreen(
        activityRetainedState: ActivityRetainedState,
        onAnimationEnd: (animator: Animator) -> Unit = {},
    ) {
        val splashScreen = installSplashScreen()
        // Add a callback that's called when the splash screen is animating to the
        // app content.
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // Create your custom animation.
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.view.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 200L

            // Call SplashScreenView.remove at the end of your custom animation.
            slideUp.doOnEnd {
                splashScreenView.remove()
                onAnimationEnd(it)
            }

            // Run your animation.
            slideUp.start()
        }

        splashScreen.setKeepOnScreenCondition {
            when (activityRetainedState.state) {
                is ActivityRetainedState.State.Loading -> true
                else -> false
            }
        }
    }

    private fun finishWhenNeeded(activityRetainedState: ActivityRetainedState) {
        if (activityRetainedState.state == ActivityRetainedState.State.OnFinish) {
            finish()
        }
    }
}