package edu.naita.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.naita.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle play offline button
        binding.playOfflineBtn.setOnClickListener {
            createOfflineGame()
        }

        // Handle play online button
        binding.playOnlineBtn.setOnClickListener {
            navigateToOnlineActivity()
        }
    }

    private fun createOfflineGame() {
        GameData.saveGameModel(
            GameModel(
                gameStatus = GameStatus.JOINED
            )
        )
        startGame()
    }

    private fun startGame() {
        startActivity(Intent(this, GameActivity::class.java))
    }

    private fun navigateToOnlineActivity() {
        val intent = Intent(this, ActivityOnline::class.java)
        startActivity(intent)
    }
}
