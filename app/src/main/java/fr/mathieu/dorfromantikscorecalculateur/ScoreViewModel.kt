package fr.mathieu.dorfromantikscorecalculateur

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {

    val scores = MutableList(25) { 0 }

    val objectiveScore = mutableIntStateOf(0)
    val flagScore = mutableIntStateOf(0)
    val unlockedScore = mutableIntStateOf(0)
    val totalScore = mutableIntStateOf(0)

    init {
        resetScore()
    }

    fun resetScore() {

    }

    fun updateScore(id: Int, newScore: String) {
        if(newScore.isNotEmpty() && newScore.length <= 3) {
            if(newScore.all { it.isDigit() }) {
                scores[id] = newScore.toInt()
            }
        }

        if(newScore.isEmpty()) {
            scores[id] = 0
        }

        updateTotalFlagScore()
        updateTotalObjectiveScore()
        updateUnlockedScore()
        updateTotalScore()

    }

    private fun updateTotalObjectiveScore() {
        objectiveScore.intValue = scores.slice(0..4).sum()
    }

    private fun updateTotalFlagScore() {
        flagScore.intValue = scores.slice(5..9).sum()
    }

    private fun updateUnlockedScore() {
        unlockedScore.intValue = scores.slice(10..24).sum()
    }

    private fun updateTotalScore() {
        totalScore.intValue = objectiveScore.intValue + flagScore.intValue + unlockedScore.intValue
    }



}