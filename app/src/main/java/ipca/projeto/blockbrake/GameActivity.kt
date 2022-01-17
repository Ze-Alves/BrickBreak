package ipca.projeto.blockbrake

import android.content.Intent
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class GameActivity : AppCompatActivity() {

    lateinit var gameView : GameView

    val storage = Firebase.storage

    val db = Firebase.firestore

    var vsID:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val display=windowManager.defaultDisplay
        var size= Point()
        display.getSize(size)

        vsID=intent.getStringExtra("vsID")



        gameView = GameView(this,size.x,size.y,vsID)

        gameView.OnGameOver={
            val intent = Intent(this@GameActivity,GameOver::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(gameView)
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

    fun OnGameEnd(){
        val intent = Intent(this@GameActivity,GameOver::class.java)
        startActivity(intent)
    }
}