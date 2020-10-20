package com.sergey.bochkin.telega.Ui.Fragments

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.mikhaellopez.circularimageview.CircularImageView
import com.sergey.bochkin.telega.Models.CommonModel
import com.sergey.bochkin.telega.R
import com.sergey.bochkin.telega.Utilites.*
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.android.synthetic.main.fragment_contacts.*

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    private lateinit var mRecycleView: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<CommonModel, ContactsHolder>
    private lateinit var mRefContacts: DatabaseReference
    private lateinit var mRefUsers: DatabaseReference
    private lateinit var mRefUsersListener: AppValueEventListener
    private var mapListeners = hashMapOf<DatabaseReference, AppValueEventListener>()


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Contacts"
        initRecycleView()
    }

    private fun initRecycleView() {
        mRecycleView = contacts_recycle_view
        mRefContacts = REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)

        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(mRefContacts, CommonModel::class.java).build()

        mAdapter = object : FirebaseRecyclerAdapter<CommonModel, ContactsHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.contact_item, parent, false)
                return ContactsHolder(view)
            }

            //Fill folder
            override fun onBindViewHolder(
                holder: ContactsHolder,
                position: Int,
                model: CommonModel
            ) {
                mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(model.id)

                mRefUsersListener = AppValueEventListener {
                    val contact = it.getCommonModel()
                    holder.name.text = contact.full_name
                    holder.status.text = contact.status
                    holder.photo.downloadAndSetImage(contact.photoUrl)
                }

                mRefUsers.addValueEventListener(mRefUsersListener)
                mapListeners[mRefUsers] = mRefUsersListener
            }
        }
        mRecycleView.adapter = mAdapter
        mAdapter.startListening()
    }

    class ContactsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.contact_full_name
        val status: TextView = view.contact_status
        val photo: CircularImageView = view.contact_photo
    }

    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
        mapListeners.forEach {
            it.key.removeEventListener(it.value)
        }
    }
}


