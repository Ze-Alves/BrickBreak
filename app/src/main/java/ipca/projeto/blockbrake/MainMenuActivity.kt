package ipca.projeto.blockbrake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import ipca.projeto.blockbrake.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        binding.textID.text= FirebaseAuth.getInstance().uid.toString()

        binding.playButton.setOnClickListener{
            val intent = Intent(this@MainMenuActivity, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.playeVsButton.setOnClickListener{

          if(binding.editTextId.text.toString()!="") {

              db.collection("users").whereEqualTo("Email", binding.editTextId.text.toString())
                  .get().addOnSuccessListener {
                      var emailid: String = ""
                      for (doc in it) {
                          emailid = doc.id
                          val intent = Intent(this@MainMenuActivity, GameActivity::class.java)
                          intent.putExtra("vsID", emailid)
                          startActivity(intent)
                          finish()
                      }
                  }

              db.collection("users").document(binding.editTextId.text.toString())
                  .get().addOnSuccessListener {
                      if (it.exists()) {
                          val intent = Intent(this@MainMenuActivity, GameActivity::class.java)
                          intent.putExtra("vsID", binding.editTextId.text.toString())
                          startActivity(intent)
                          finish()
                      }
                      // else

                      // Toast.makeText(
                      //     baseContext, "ID Nao Existe",
                      //     Toast.LENGTH_SHORT).show()

                  }.addOnFailureListener {
                      Toast.makeText(
                          baseContext, "Falha ao Tentar Ler",
                          Toast.LENGTH_SHORT
                      ).show()
                  }
          }else
              Toast.makeText(
                  baseContext, "Campo de ID Vazio",
                  Toast.LENGTH_SHORT
              ).show()




        }

        binding.buttonLogout.setOnClickListener{
                Firebase.auth.signOut()
            val intent = Intent(this@MainMenuActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}