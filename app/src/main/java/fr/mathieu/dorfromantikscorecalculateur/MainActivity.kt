package fr.mathieu.dorfromantikscorecalculateur

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.mathieu.dorfromantikscorecalculateur.ui.theme.DorfromantikScoreCalculateurTheme

class MainActivity: ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DorfromantikScoreCalculateurTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { AppTopBar() }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ScoreLayout()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = { Text("Calculateur de score Dorfromantik", style = MaterialTheme.typography.titleLarge) }
    )
}

@Composable
fun ScoreLayout(
    modifier: Modifier = Modifier,
    scoreViewModel: ScoreViewModel = viewModel()
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        val scoreSections = listOf(
            Triple(
                "Objectifs",
                scoreViewModel.scores.slice(0..4),
                listOf("Forêt", "Champ", "Maison", "Rail", "Eau")
            ),
            Triple(
                "Drapeaux",
                scoreViewModel.scores.slice(5..9),
                listOf("Forêt", "Champ", "Maison", "Rail (Le plus long)", "Eau (La plus longue)")
            ),
            Triple(
                "Déverrouillé",
                scoreViewModel.scores.slice(10..24),
                listOf(
                    "Coeurs Rouges (1/côté correspondant)",
                    "Cirque (entouré = 10)",
                    "Aiguilleur (2/passage à niveau)",
                    "Bergere (1/mouton)",
                    "Colline (distance 2 = 2/Tuile Objectif)",
                    "Chantier (7+ territoires = 7)",
                    "Site de Ballons (2/distance)",
                    "Coeur d'or (2/côté correspondant)",
                    "Cabane Forestiere (Objectifs - Forêts)",
                    "Fête des moissons (Objectifs - Champs)",
                    "Tour de Guet (Objectifs - Maisons)",
                    "Locomotive (Objectifs - Rails)",
                    "Navire (Objectifs - Eau)",
                    "Gare (fermée = 1/tuile)",
                    "Port (fermé = 1/tuile)"
                )
            ),
        )

        scoreSections.forEachIndexed { index, (title, scores, names) ->
            ScoreSection(
                modifier = modifier,
                title = title,
                scores = scores,
                colors = getScoreSectionColors(index),
                scoreNames = names.map { it.substringBefore("(").trim() },
                scoreSubtitles = names.map { it.substringAfter("(", "").removeSuffix(")") },
                onScoreValueChanged = { i, value ->
                    scoreViewModel.updateScore(i + index * 5, value)
                },
                totalScore = when (index) {
                    0 -> scoreViewModel.objectiveScore.intValue
                    1 -> scoreViewModel.flagScore.intValue
                    else -> scoreViewModel.unlockedScore.intValue
                }
            )


                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f) // Light gray divider
                )

        }



        GlobalScore(viewModel = scoreViewModel)
    }
}

@Composable
fun getScoreSectionColors(index: Int): List<Color> {
    return when (index) {
        0 -> listOf(
            Color(0xFF2E7D32),  // Vibrant Green
            Color(0xFFFBC02D),  // Bright Yellow
            Color(0xFFD32F2F),  // Saturated Red
            Color(0xFF8D6E63),  // Elegant Brown
            Color(0xFF1E88E5)   // Eye-catching Blue
        )
        1 -> listOf(
            Color(0xFF388E3C),  // Balanced Green
            Color(0xFFFBC02D),  // Vivid Yellow
            Color(0xFFD32F2F),  // Deep Red
            Color(0xFF8D6E63),  // Warm Brown
            Color(0xFF0288D1)   // Rich Blue
        )
        else -> List(15) { Color(0xFFBDBDBD) }  // Neutral Gray fallback
    }
}

@Composable
fun GlobalScore(viewModel: ScoreViewModel) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Score Global",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = viewModel.totalScore.intValue.toString(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun ScoreSection(
    modifier: Modifier = Modifier,
    title: String,
    colors: List<Color>,
    scores: List<Int>,
    scoreNames: List<String>,
    scoreSubtitles: List<String> = listOf(),
    onScoreValueChanged: (Int, String) -> Unit,
    totalScore: Int
) {
    Surface(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            scores.forEachIndexed { index, score ->
                ScoreRow(
                    circleColor = colors[index],
                    scoreName = scoreNames[index],
                    scoreSubtitle = scoreSubtitles.getOrNull(index).orEmpty(),
                    scoreValue = score.toString(),
                    onScoreValueChanged = { value -> onScoreValueChanged(index, value) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Score Total",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = totalScore.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ScoreRow(
    circleColor: Color,
    scoreName: String,
    scoreSubtitle: String,
    scoreValue: String,
    onScoreValueChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreCardIcon(color = circleColor)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = scoreName,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                if (scoreSubtitle.isNotEmpty()) {
                    Text(
                        text = scoreSubtitle,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        TextField(
            value = if (scoreValue.toIntOrNull() == 0) "" else scoreValue,
            onValueChange = onScoreValueChanged,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 8.dp)
        )
    }
}

@Composable
fun ScoreCardIcon(color: Color) {
    Canvas(modifier = Modifier.size(32.dp)) {
        drawCircle(color = color)
    }
}

