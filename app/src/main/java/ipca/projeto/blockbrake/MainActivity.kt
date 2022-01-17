package ipca.projeto.blockbrake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.projeto.blockbrake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private  lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val storage = Firebase.firestore


        binding.LogBottun.setOnClickListener {



            if(binding.editPassword.text.toString()!=""&&binding.editTextEmail.text.toString()!="")
                auth.signInWithEmailAndPassword(
                    binding.editTextEmail.text.toString(),
                    binding.editPassword.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val intent = Intent(this@MainActivity, MainMenuActivity::class.java)
                            startActivity(intent)
                            finish()


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(
                                baseContext, "Email ou Password Invalidos",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

            else Toast.makeText(
                baseContext, "Email ou Password Invalidos",
                Toast.LENGTH_SHORT
            ).show()

        }

        binding.buttonRegister.setOnClickListener {
          val intent =Intent(this@MainActivity,RegisterActivity::class.java)
          startActivity(intent)
          finish()





        }
    }
}