package com.sergey.bochkin.telega.Ui.Fragments

import android.util.Log
import com.sergey.bochkin.telega.R
import com.sergey.bochkin.telega.Utilites.*
import kotlinx.android.synthetic.main.fragment_change_user_name.*
import java.util.*


class ChangeUserNameFragment : BaseChangeFragment(R.layout.fragment_change_user_name) {
    lateinit var mNewUserName:String
    override fun onResume() {
        super.onResume()
        settings_input_username.setText(USER.username)
    }
    override fun change() {
        mNewUserName = settings_input_username.text.toString().toLowerCase(Locale.getDefault())
        if(mNewUserName.isEmpty()){
            showToast(getString(R.string.toast_empty_username))
        }
        else{
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener{
                    if(it.hasChild(mNewUserName)){
                        showToast("User with $mNewUserName username already exist")
                    }
                    else{
                        changeUserName()
                    }
                })
        }
    }

    private fun changeUserName() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUserName).setValue(CURRENT_UID)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    updateCurrentUserName()
                }
            }
    }

    private fun updateCurrentUserName() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_USERNAME)
            .setValue(mNewUserName)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    showToast(getString(R.string.toast_data_updated))
                    deleteOldUserName()
                }
                else{
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun deleteOldUserName() {
        Log.d("MyLog", USER.username)
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    showToast(getString(R.string.toast_data_updated))
                    fragmentManager?.popBackStack()
                    USER.username = mNewUserName
                }
                else{
                    showToast(it.exception?.message.toString())
                }
            }
    }
}