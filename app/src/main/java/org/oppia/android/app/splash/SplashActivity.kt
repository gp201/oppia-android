package org.oppia.android.app.splash

import android.os.Bundle
import org.oppia.android.app.activity.InjectableAppCompatActivity
import org.oppia.android.app.deprecation.DeprecationNoticeExitAppListener
import javax.inject.Inject

/** An activity that shows a temporary loading page until the app is fully loaded then navigates to [ProfileActivity]. */
class SplashActivity : InjectableAppCompatActivity(), DeprecationNoticeExitAppListener {

  @Inject
  lateinit var splashActivityPresenter: SplashActivityPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    activityComponent.inject(this)
    splashActivityPresenter.handleOnCreate()
  }

  override fun onCloseAppButtonClicked() = splashActivityPresenter.handleOnCloseAppButtonClicked()
}
