package ipca.projeto.blockbrake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ipca.projeto.blockbrake.databinding.ActivityGameOverBinding
import ipca.projeto.blockbrake.databinding.ActivityMainBinding

class GameOver : AppCompatActivity() {
    lateinit var binding: ActivityGameOverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonReset.setOnClickListener{
            val intent = Intent(this@GameOver, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}