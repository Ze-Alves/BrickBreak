package ipca.projeto.blockbrake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.projeto.blockbrake.databinding.ActivityMainBinding
import ipca.projeto.blockbrake.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        var storage = Firebase.firestore

        binding.RegButton.setOnClickListener {

            if(binding.editEmailReg.text.toString()!=""&&binding.editPassReg.text.toString()!=""
                &&binding.editTextName.text.toString()!="")
        auth.createUserWithEmailAndPassword(binding.editEmailReg.text.toString(),
            binding.editPassReg.text.toString())
            .addOnCompleteListener(this) { task ->

               if (task.isSuccessful) {

                   val uid=FirebaseAuth.getInstance().uid.toString()

                    val user = hashMapOf(
                        "Name" to binding.editTextName.text.toString(),
                        "LastPoints" to 0,
                        "MaxPoints" to 0,
                        "Email" to binding.editEmailReg.text.toString()
                    )

                    storage.collection("users").document(uid)
                        .set(user)

                   val intent = Intent(this@RegisterActivity,MainMenuActivity::class.java)
                                 startActivity(intent)
                                 finish()
                } else {
                    Toast.makeText(baseContext, "Email ou Password Invalidos",
                        Toast.LENGTH_SHORT).show()
                }
            }
            else Toast.makeText(baseContext, "Email ou Password Invalidos",
                Toast.LENGTH_SHORT).show()
        }
    }
}