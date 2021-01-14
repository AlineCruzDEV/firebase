package com.digitalhouse.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.firebaseauth.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null)
            callMain(user.uid, user.email.toString())

        binding.tvCreateLogin.setOnClickListener {
            callRegister()
        }

        binding.btnLogin.setOnClickListener{
            getDataFields()
        }
    }

    private fun callRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun callMain(key: String, email: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("key", key)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    fun getDataFields(){
        val email = binding.edEmailLogin.text.toString()
        val password = binding.edPasswordLogin.text.toString()
        val empEmail = email.isNotEmpty()
        val empPassword = password.isNotEmpty()

        if (empEmail && empPassword)
            sendDataFirebase(email, password)
        else
            showMsg("Preencha todos os dados")
    }
    //valida informações login
    fun sendDataFirebase(email: String, pwd: String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pwd)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result?.user!!
                    val key = firebaseUser.uid
                    val email = firebaseUser.email.toString()
                    showMsg("CAdastro Realizado com sucesso!")
                    callMain(key, email)
                } else {
                    showMsg(task.exception.toString())
                }
            }
    }



    fun showMsg(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }




}