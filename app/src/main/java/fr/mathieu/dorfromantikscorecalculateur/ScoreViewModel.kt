package fr.mathieu.dorfromantikscorecalculateur

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {

    // A list to store scores, initialized with 25 zeros
    val scores = MutableList(25) { 0 }

    val objectiveScore = mutableIntStateOf(0)
    val flagScore = mutableIntStateOf(0)
    val unlockedScore = mutableIntStateOf(0)
    val totalScore = mutableIntStateOf(0)

    // Reset all scores to 0
    fun resetScores() {
        scores.fill(0)  // Fills the entire list with zeros
        updateScores()
    }

    // Update a specific score by id (index) with a new value
    fun updateScore(id: Int, newScore: String) {
        val score = newScore.toIntOrNull()
        scores[id] = score?.takeIf { it in 0..999 } ?: 0 // Validates score and applies it
        updateScores()  // Recalculate total scores after the update
    }

    // Updates total scores after any score change
    private fun updateScores() {
        updateTotalObjectiveScore()
        updateTotalFlagScore()
        updateUnlockedScore()
        updateTotalScore()
    }

    // Calculate and update the objective score (sum of first 5 items)
    private fun updateTotalObjectiveScore() {
        objectiveScore.intValue = scores.slice(0..4).sum()
    }

    // Calculate and update the flag score (sum of items 6-10)
    private fun updateTotalFlagScore() {
        flagScore.intValue = scores.slice(5..9).sum()
    }

    // Calculate and update the unlocked score (sum of items 11-25)
    private fun updateUnlockedScore() {
        unlockedScore.intValue = scores.slice(10..24).sum()
    }

    // Calculate and update the overall total score
    private fun updateTotalScore() {
        totalScore.intValue = objectiveScore.intValue + flagScore.intValue + unlockedScore.intValue
    }
}