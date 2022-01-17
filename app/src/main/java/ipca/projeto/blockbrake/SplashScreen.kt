package ipca.projeto.blockbrake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (FirebaseAuth.getInstance().currentUser == null ){
            val intent = Intent ( this, MainActivity::class.java)
            Toast.makeText(baseContext, "oiii", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }else{
            val intent = Intent ( this,MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}