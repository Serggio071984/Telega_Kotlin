package com.sergey.bochkin.telega.Ui.Fragments

import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.sergey.bochkin.telega.Activities.RegisterActivity
import com.sergey.bochkin.telega.MainActivity
import com.sergey.bochkin.telega.R
import com.sergey.bochkin.telega.Utilites.AUTH
import com.sergey.bochkin.telega.Utilites.replaceActivity
import com.sergey.bochkin.telega.Utilites.replaceFragment
import com.sergey.bochkin.telega.Utilites.showToast
import kotlinx.android.synthetic.main.fragment_enter_phone.*
import java.util.concurrent.TimeUnit


class EnterPhoneFragment : Fragment(R.layout.fragment_enter_phone) {

    private lateinit var mPhoneNumber: String
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onStart() {
        super.onStart()
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        showToast("Welcome!!!")
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    }
                    else{
                        showToast(task.exception?.message.toString())
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                  showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNumber, id))
            }
        }
        register_btn_next.setOnClickListener{sendCode()}
    }
    private fun sendCode() {
        if(register_input_phone.text.toString().isEmpty()){
            showToast(getString(R.string.register_toast_enter_phone))
        }
        else{

            authUser()
            //replaceFragment(EnterCodeFragment())
        }
    }

    private fun authUser() {
        mPhoneNumber = register_input_phone.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mPhoneNumber, 60, TimeUnit.SECONDS, activity as RegisterActivity, mCallback)
    }
}