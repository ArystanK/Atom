package kz.maxwell.atom.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_profile.*
import kz.maxwell.atom.*


class ProfileFragment : Fragment() {

    private lateinit var purchaseAdapter: PurchaseRecyclerAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var storageRef: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        storageRef = Firebase.storage.reference
        db = Firebase.firestore
        mAuth = FirebaseAuth.getInstance()
        val email = mAuth.currentUser!!.email
        setProfileData(email!!)
        setProfilePicture(email)

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        addDataSet()

        logOutButton.setOnClickListener {
            AlertDialog.Builder(activity)
                .setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    mAuth.signOut()
                    MainActivity().finish()
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    // Move it to ViewModel
    private fun setProfileData(email: String) {
        var profileData: String
        db.collection("users")
            .document(email)
            .get()
            .addOnSuccessListener {
                profileData = it["first_name"].toString() + " " + it["last_name"].toString()
                userName.text = profileData
            }
    }

    // Move it to ViewModel
    private fun setProfilePicture(email: String) {
        storageRef.child("profile_pics/$email").downloadUrl.addOnSuccessListener {
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(this)
                .applyDefaultRequestOptions(requestOptions)
                .load(it)
                .into(profilePictureImageView)
        }
    }

    private fun addDataSet() {
        val data = DataSource.createDataSetPurchase()
        purchaseAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        purchaseRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            val topSpacingItemDecoration = TopSpacingItemDecoration(5)
            addItemDecoration(topSpacingItemDecoration)
            purchaseAdapter = PurchaseRecyclerAdapter()
            adapter = purchaseAdapter
        }
    }
}