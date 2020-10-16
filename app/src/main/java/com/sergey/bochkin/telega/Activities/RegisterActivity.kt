package com.sergey.bochkin.telega.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.sergey.bochkin.telega.R
import com.sergey.bochkin.telega.Ui.Fragments.EnterPhoneFragment
import com.sergey.bochkin.telega.Utilites.initFireBase
import com.sergey.bochkin.telega.Utilites.replaceFragment
import com.sergey.bochkin.telega.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRegisterBinding
private lateinit var mToolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFireBase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolBar
        setSupportActionBar(mToolbar)
        title=getString(R.string.register_title_yourPhone)
        replaceFragment(EnterPhoneFragment(), false)
    }
}