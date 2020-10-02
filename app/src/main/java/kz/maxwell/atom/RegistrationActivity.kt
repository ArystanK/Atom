package kz.maxwell.atom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_registration.*


class RegistrationActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Регистрация"
        storage = Firebase.storage
        storageReference = storage.reference
        auth = Firebase.auth
        db = Firebase.firestore

        profilePicture.setOnClickListener {
            choosePicture()
        }

        signUpButton.setOnClickListener {
            val firstName: String = firstNameEditText.text.toString()
            val lastName: String = lastNameEditText.text.toString()
            val email: String = emailEditTextReg.text.toString()
            val password: String = passwordEditTextReg.text.toString()
            val validPassword =
                password == repeatedPasswordEditText.text.toString() && password.length >= 8

            if (validPassword && isValidPassword(password)) {
                createAccount(firstName, lastName, email, password)
            } else {
                Toast.makeText(this@RegistrationActivity, "Invalid password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isValidPassword(password: String): Boolean {
        var lowerCase = false
        var numbers = false
        for (i in password) {
            if (i in 'a'..'z') {
                lowerCase = true
            }
            if (i in '0'..'9') {
                numbers = true
            }
        }
        return lowerCase && numbers
    }

    private fun choosePicture() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            profilePicture.setImageURI(imageUri)
        }
    }

    private fun uploadPicture(email: String) {
        if (imageUri != null) {
            val riversRef: StorageReference? =
                storageReference.child("profile_pics/$email")

            riversRef?.putFile(imageUri!!)
        }
    }

    private fun createAccount(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        relativeLayoutReg.visibility = View.VISIBLE // Show progressBar

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    relativeLayoutReg.visibility = View.GONE
                    Toast.makeText(this, "Вы успешно зарегестрированы!", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    relativeLayoutReg.visibility = View.GONE
                }
            }

        // Adding user to db
        val newUser = hashMapOf(
            "first_name" to firstName,
            "last_name" to lastName
        )

        db.collection("users").document(email).set(newUser)
        uploadPicture(email)
    }
}