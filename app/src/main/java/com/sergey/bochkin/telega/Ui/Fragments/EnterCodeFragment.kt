package com.sergey.bochkin.telega.Ui.Fragments

import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.sergey.bochkin.telega.Activities.RegisterActivity
import com.sergey.bochkin.telega.MainActivity
import com.sergey.bochkin.telega.R
import com.sergey.bochkin.telega.Utilites.*
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(val phoneNumber: String, val id: String) :
    Fragment(R.layout.fragment_enter_code) {
    override fun onStart() {
        super.onStart()

        (activity as RegisterActivity).title = phoneNumber

        register_input_code.addTextChangedListener(AppTextWatcher {
            val code = register_input_code.text.toString()
            if (code.length == 6) {
                enterCode(code)
            }
        })
    }

    private fun enterCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(id, code)

        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                dateMap[CHILD_USERNAME] = uid

                REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                    .addOnCompleteListener { taskOnUpdate ->
                        if (taskOnUpdate.isSuccessful) {
                            showToast("Welcome!!!")
                            (activity as RegisterActivity).replaceActivity(MainActivity())
                        } else {
                            showToast(taskOnUpdate.exception?.message.toString())
                        }
                    }
            } else {
                showToast(task.exception?.message.toString())
            }
        }
    }
}