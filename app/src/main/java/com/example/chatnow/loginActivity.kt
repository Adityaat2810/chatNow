package com.example.chatnow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class loginActivity : AppCompatActivity() {

    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnSignIn:Button
    private lateinit var mAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth=FirebaseAuth.getInstance()

        edtEmail=findViewById(R.id.Email)
        edtPassword=findViewById(R.id.Password)
        btnLogin=findViewById(R.id.login_Button)
        btnSignIn=findViewById(R.id.signUp_Button)

        btnSignIn.setOnClickListener{
            val intent =Intent(this,signinActivity::class.java)
            startActivity(intent)
        }

       btnLogin.setOnClickListener{
           val email =edtEmail.text.toString()
           val password=edtPassword.text.toString()

           login(email,password);
       }

    }
    private fun login(email:String ,password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // log in user
                    val intent= Intent(this@loginActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@loginActivity,"User does not exist"
                    ,Toast.LENGTH_SHORT).show()

                }
            }

    }
}