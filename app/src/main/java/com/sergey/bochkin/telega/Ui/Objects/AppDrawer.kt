package com.sergey.bochkin.telega.Ui.Objects

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.sergey.bochkin.telega.R
import com.sergey.bochkin.telega.Ui.Fragments.ContactsFragment
import com.sergey.bochkin.telega.Ui.Fragments.SettingsFragment
import com.sergey.bochkin.telega.Utilites.APP_ACTIVITY
import com.sergey.bochkin.telega.Utilites.USER
import com.sergey.bochkin.telega.Utilites.downloadAndSetImage
import com.sergey.bochkin.telega.Utilites.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*

class AppDrawer {
    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    private lateinit var mDarawerLayout: DrawerLayout
    private lateinit var mCurentProfile: ProfileDrawerItem


    fun create(){
        initLoader()
        createHeader()
        createDrawer()
        mDarawerLayout = mDrawer.drawerLayout
    }
    fun disableDrawer(){
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mDarawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener{
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
    }
    fun enableDrawer(){
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        mDarawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener{
            mDrawer.openDrawer()
        }
    }
    private fun createDrawer() {
        mDrawer = DrawerBuilder().withActivity(APP_ACTIVITY)
            .withToolbar(APP_ACTIVITY.mToolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Create Group").withIconTintingEnabled(true)
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_create_groups),
                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Secret Chat").withIconTintingEnabled(true)
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_secret_chat),
                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Create Channel").withIconTintingEnabled(true)
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_create_channel),
                PrimaryDrawerItem().withIdentifier(103)
                    .withIconTintingEnabled(true)
                    .withName("Contacts").withIconTintingEnabled(true)
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_contacts),
                PrimaryDrawerItem().withIdentifier(104)
                    .withIconTintingEnabled(true)
                    .withName("Calls").withIconTintingEnabled(true)
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_phone),
                PrimaryDrawerItem().withIdentifier(105)
                    .withIconTintingEnabled(true)
                    .withName("Favorites").withIconTintingEnabled(true)
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_favorites),
                PrimaryDrawerItem().withIdentifier(106)
                    .withIconTintingEnabled(true)
                    .withName("Settings").withIconTintingEnabled(true)
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_settings),
                DividerDrawerItem(),
                PrimaryDrawerItem().withIdentifier(107)
                    .withIconTintingEnabled(true)
                    .withName("Invite Friends").withIconTintingEnabled(true)
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_invate),
                PrimaryDrawerItem().withIdentifier(107)
                    .withIconTintingEnabled(true)
                    .withName("Help").withIconTintingEnabled(true)
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_help)
            ).withOnDrawerItemClickListener(object :Drawer.OnDrawerItemClickListener{
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    Toast.makeText(APP_ACTIVITY.applicationContext, "position: $position", Toast.LENGTH_SHORT).show()
                    clickToItem(position)
                    return false
                }
            }).build()
    }

    private fun clickToItem(position:Int){
        when(position){
            7 -> APP_ACTIVITY.replaceFragment(SettingsFragment())
            4 -> APP_ACTIVITY.replaceFragment(ContactsFragment())
        }
    }

    private fun createHeader() {
        mCurentProfile = ProfileDrawerItem()
            .withName(USER.full_name)
            .withEmail(USER.phone)
            .withIcon(USER.photoUrl)
            .withIdentifier(200)
        mHeader = AccountHeaderBuilder().withActivity(APP_ACTIVITY).withHeaderBackground(R.drawable.header)
            .addProfiles(
                mCurentProfile
            ).build()
    }
    fun updateHeader(){
        mCurentProfile
            .withName(USER.full_name)
            .withEmail(USER.phone)
            .withIcon(USER.photoUrl)
        mHeader.updateProfile(mCurentProfile)
    }
    private fun initLoader(){
        DrawerImageLoader.init(object : AbstractDrawerImageLoader(){
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                imageView.downloadAndSetImage(uri.toString())
            }
        })
    }
}