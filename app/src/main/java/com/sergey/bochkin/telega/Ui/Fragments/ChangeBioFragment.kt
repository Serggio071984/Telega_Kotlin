package com.sergey.bochkin.telega.Ui.Fragments

import com.sergey.bochkin.telega.R
import com.sergey.bochkin.telega.Utilites.*
import kotlinx.android.synthetic.main.fragment_change_bio.*


class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        settings_input_bio_text.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio_text.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_BIO)
            .setValue(newBio)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    showToast(getString(R.string.toast_data_updated))
                    USER.bio = newBio
                    fragmentManager?.popBackStack()
                }
            }
    }
}