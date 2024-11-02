package edu.naita.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import edu.naita.tictactoe.databinding.ActivityOnlineBinding
import kotlin.random.Random
import kotlin.random.nextInt

class ActivityOnline : AppCompatActivity() {

    private lateinit var binding: ActivityOnlineBinding

    private var gameModel: GameModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createOnlineBtn.setOnClickListener {
            createOnlineGame()
        }
        binding.joinOnlineBtn.setOnClickListener {
            joinOnlineGame()
        }
    }
    private fun createOnlineGame(){
        GameData.myId = "X"
        GameData.saveGameModel(
            GameModel(
                gameStatus = GameStatus.CREATED,
                gameId = Random.nextInt(1000..9999).toString()
            )
        )
        startGame()
    }
    private fun joinOnlineGame(){
        var gameId = binding.gameIdInput.text.toString()
        if(gameId.isEmpty()){
            binding.gameIdInput.setError("Please enter game id")
                return
        }
        GameData.myId = "O"
        Firebase.firestore.collection("games")
            .document(gameId)
            .get()
            .addOnSuccessListener {
                val model = it.toObject(GameModel::class.java)
                if(model==null){
                    binding.gameIdInput.setError("Please enter valid game id")
                }else{
                    model.gameStatus = GameStatus.JOINED
                    GameData.saveGameModel(model)
                    startGame()
                }
            }
    }
    private fun startGame() {
        startActivity(Intent(this, GameActivity::class.java))
    }

}

