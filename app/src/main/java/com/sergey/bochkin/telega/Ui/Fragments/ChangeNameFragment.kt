package com.sergey.bochkin.telega.Ui.Fragments

import com.sergey.bochkin.telega.R
import com.sergey.bochkin.telega.Utilites.*
import kotlinx.android.synthetic.main.fragment_change_name.*

class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {
    override fun onResume() {
        super.onResume()
        initFullNameList()
    }

    private fun initFullNameList() {
        val fullNameList = USER.full_name.split(" ")
        if (fullNameList != null && fullNameList.isNotEmpty()) {
            settings_input_name.setText(fullNameList[0])
            if (fullNameList.size > 1) {
                settings_input_surname.setText(fullNameList[1])
            }
        }
    }

    override fun change() {
        val name = settings_input_name.text.toString()
        val surname = settings_input_surname.text.toString()

        if (name.isEmpty()) {
            showToast(getString(R.string.name_cannot_be_empty))

        } else {
            val fullName = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULLNAME)
                .setValue(fullName).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_updated))
                        USER.full_name = fullName
                        APP_ACTIVITY.mAppDrawer.updateHeader()
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}