package kz.maxwell.atom.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileViewModel(
    private val email: String,
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _userName = MutableLiveData<String>()
    private val _profilePicture = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName
    val profilePicture: LiveData<String>
        get() = _profilePicture

    suspend fun getUserName() {
        val user = db.collection("users").document(email).get().result!!
        val firstName = user["first_name"]
        val lastName = user["last_name"]
        val userName = firstName.toString() + lastName.toString()
        _userName.value = userName
    }
}