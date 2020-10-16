package com.sergey.bochkin.telega

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.sergey.bochkin.telega.Activities.RegisterActivity
import com.sergey.bochkin.telega.Models.User
import com.sergey.bochkin.telega.Ui.Fragments.ChatsFragment
import com.sergey.bochkin.telega.Ui.Objects.AppDrawer
import com.sergey.bochkin.telega.Utilites.*
import com.sergey.bochkin.telega.databinding.ActivityMainBinding
import com.theartofdev.edmodo.cropper.CropImage

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        APP_ACTIVITY = this
        initFireBase()
        initUser{
            initFields()
            initFunc()
        }
    }
    private fun initFunc() {
        if(AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(), false)
        }
        else{
            replaceActivity(RegisterActivity())
        }
    }
    private fun initFields() {
        mToolbar = mainBinding.mainToolBar
        mAppDrawer = AppDrawer(this, mToolbar)

    }

    override fun onStart() {
        super.onStart()
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        AppStates.updateState(AppStates.OFFLINE)
    }
}