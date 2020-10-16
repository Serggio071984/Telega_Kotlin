package com.sergey.bochkin.telega.Ui.Fragments


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.google.firebase.storage.StorageReference
import com.sergey.bochkin.telega.Activities.RegisterActivity
import com.sergey.bochkin.telega.R
import com.sergey.bochkin.telega.Utilites.*
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        ininеFields()
    }

    private fun ininеFields() {
        settings_bio.text = USER.bio
        settings_fullname.text = USER.full_name
        settings_phoneNumber.text = USER.phone
        settings_status.text = USER.status
        settings_username.text = USER.username
        settings_btn_changeUserName.setOnClickListener { replaceFragment(ChangeUserNameFragment()) }
        settings_btn_changeBio.setOnClickListener { replaceFragment(ChangeBioFragment()) }
        settings_change_photo.setOnClickListener { changePhotoUser() }
        settings_user_photo.downloadAndSetImage(USER.photoUrl)
    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1,1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY, this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
     when(item.itemId){
         R.id.settings_menu_exit -> {
             AUTH.signOut()
             (APP_ACTIVITY).replaceActivity(RegisterActivity())
         }
         R.id.settings_menu_change_name ->{
             replaceFragment(ChangeNameFragment())
         }
     }
        return true
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK
            && data != null){
            val uri: Uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE).child(CURRENT_UID)

            putImageToStorage(uri, path){
                getUrlFromStorage(path){
                    putUrlToDataBase(it){
                        settings_user_photo.downloadAndSetImage(it)
                        showToast(getString(R.string.toast_data_updated))
                        USER.photoUrl = it
                        APP_ACTIVITY.mAppDrawer.updateHeader()
                    }
                }
            }
        }
    }
}