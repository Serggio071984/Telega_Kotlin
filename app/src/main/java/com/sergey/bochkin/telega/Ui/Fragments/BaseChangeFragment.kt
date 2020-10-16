package com.sergey.bochkin.telega.Ui.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import com.sergey.bochkin.telega.MainActivity
import com.sergey.bochkin.telega.R
import com.sergey.bochkin.telega.Utilites.APP_ACTIVITY
import com.sergey.bochkin.telega.Utilites.hideKeyBoard


open class BaseChangeFragment(layout: Int) : Fragment(layout) {
    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        APP_ACTIVITY.mAppDrawer.disableDrawer()
        hideKeyBoard()
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_chnage -> change()
        }
        return true
    }

    open fun change() {

    }
}