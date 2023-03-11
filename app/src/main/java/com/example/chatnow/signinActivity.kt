package com.example.chatnow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.sql.Date

class signinActivity : AppCompatActivity() {

    private lateinit var edtname: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide()


        mAuth=FirebaseAuth.getInstance()

        edtname=findViewById(R.id.namesi)
        edtEmail=findViewById(R.id.emailsi)
        edtPassword=findViewById(R.id.passwordsi)
        btnSignIn=findViewById(R.id.signupsi)

        btnSignIn.setOnClickListener{
            val name=edtname.text.toString()
            val email = edtEmail.text.toString()
            val password=edtPassword.text.toString()

            signUp(name,email,password)
        }
    }

    private fun signUp(name:String,email:String,password:String){
        //logic for logging user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                //code for jumping to home
                    //add user to database
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)

                    val intent= Intent(this@signinActivity,MainActivity::class.java)
                    finish()
                     startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                     Toast.makeText(this@signinActivity,"Some error occured"
                         ,Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDatabase(name: String,email: String,uid:String){
        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(user(name,email, uid))
    }
}