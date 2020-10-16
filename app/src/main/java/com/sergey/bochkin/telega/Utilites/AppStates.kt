package com.sergey.bochkin.telega.Utilites

enum class AppStates(val state:String) {
    ONLINE("OnLine"),
    OFFLINE("Offline"),
    TYPING("typing");

    companion object{
        fun updateState(appStates: AppStates){
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_STATUS)
                .setValue(appStates.state)
                .addOnSuccessListener { USER.status = appStates.state}
                .addOnFailureListener{
                    showToast(it.message.toString())
                }
        }
    }
}