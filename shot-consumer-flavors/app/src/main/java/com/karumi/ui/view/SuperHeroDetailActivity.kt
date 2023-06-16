package com.karumi.ui.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.karumi.R
import com.karumi.domain.model.SuperHero
import com.karumi.domain.usecase.GetSuperHeroByName
import com.karumi.ui.presenter.SuperHeroDetailPresenter
import com.karumi.ui.utils.setImageBackground

class SuperHeroDetailActivity : BaseActivity(), SuperHeroDetailPresenter.View {
    private val iv_super_hero_photo by lazy { findViewById<ImageView>(R.id.iv_super_hero_photo) }
    private val iv_avengers_badge by lazy { findViewById<ImageView>(R.id.iv_avengers_badge) }
    private val tv_super_hero_description by lazy { findViewById<TextView>(R.id.tv_super_hero_description) }
    private val tv_super_hero_name by lazy { findViewById<TextView>(R.id.tv_super_hero_name) }
    private val progress_bar by lazy { findViewById<androidx.core.widget.ContentLoadingProgressBar>(R.id.progress_bar) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    companion object {
        private const val SUPER_HERO_NAME_KEY = "super_hero_name_key"

        fun open(activity: Activity, superHeroName: String) {
            val intent = Intent(activity, SuperHeroDetailActivity::class.java)
            intent.putExtra(SUPER_HERO_NAME_KEY, superHeroName)
            activity.startActivity(intent)
        }
    }

    override val presenter: SuperHeroDetailPresenter by injector.instance()

    override val layoutId: Int = R.layout.super_hero_detail_activity
    override val toolbarView: Toolbar
        get() = toolbar

    override fun preparePresenter(intent: Intent?) {
        val superHeroName = intent?.extras?.getString(SUPER_HERO_NAME_KEY)
        title = superHeroName
        presenter.preparePresenter(superHeroName)
    }

    override fun close() = finish()

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showSuperHero(superHero: SuperHero) {
        tv_super_hero_name.text = superHero.name
        tv_super_hero_description.text = superHero.description
        iv_avengers_badge.visibility =
            if (superHero.isAvenger) View.VISIBLE else View.GONE
        iv_super_hero_photo.setImageBackground(superHero.photo)
    }

    override val activityModules = Module(allowSilentOverride = true) {
        bind<SuperHeroDetailPresenter>() with provider {
            SuperHeroDetailPresenter(this@SuperHeroDetailActivity, instance())
        }
        bind<GetSuperHeroByName>() with provider { GetSuperHeroByName(instance()) }
    }
}