package com.karumi

import android.app.Application
import android.content.Context
import com.karumi.shot.ShotTestRunner

class TestRunner : ShotTestRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, className, context)
    }

    override fun callApplicationOnCreate(app: Application) {
        app.asApp().kodein.mutable = true
        super.callApplicationOnCreate(app)
    }
}