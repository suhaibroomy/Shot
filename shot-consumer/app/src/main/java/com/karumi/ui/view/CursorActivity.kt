package com.karumi.ui.view

import androidx.appcompat.widget.Toolbar
import android.view.View
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.karumi.R
import com.karumi.ui.presenter.CursorPresenter
import android.widget.EditText

class CursorActivity : BaseActivity(), CursorPresenter.View {

    override val layoutId: Int = R.layout.cursor_activity
    override val presenter: CursorPresenter by injector.instance()

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val et_cursor by lazy { findViewById<EditText>(R.id.et_cursor) }

    override val toolbarView: Toolbar
        get() = toolbar

    override val activityModules = Kodein.Module(allowSilentOverride = true) {
        bind<CursorPresenter>() with provider {
            CursorPresenter(this@CursorActivity)
        }
    }

    override fun showEditText() {
        et_cursor.visibility = View.VISIBLE
    }
}