package com.digitalhouse.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.digitalhouse.firebaseauth.databinding.ActivityLoginBinding
import com.digitalhouse.firebaseauth.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener{
            getDataFields()
        }
    }

    //captura os dados digitados dos campos
    fun getDataFields(){
        val email = binding.edEmailRegister.text.toString()
        val pwd = binding.edPasswordRegister.text.toString()
        val aptEmail = email.isNotEmpty()
        val aptPassword = pwd.isNotEmpty()

        if(aptEmail && aptPassword)
            sendDataFirebase(email, pwd)
        else
            showMsg("Preencha todos os dados")

    }

    //envia cadastro para o firebase
    fun sendDataFirebase(email: String, pwd: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pwd)
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

    private fun callMain(key: String, email: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("key", key)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    fun showMsg(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}