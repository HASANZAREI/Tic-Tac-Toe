package com.example.tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.Pink200
import com.example.tictactoe.ui.theme.Pink300
import com.example.tictactoe.ui.theme.Pink500
import com.example.tictactoe.ui.theme.TicTacToeTheme
import com.example.tictactoe.ui.theme.Yellow200
import com.example.tictactoe.ui.theme.Yellow500

class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                Scaffold { paddingValues ->
                    Surface(modifier = Modifier.padding(paddingValues)) {
                        var win by remember {
                            mutableStateOf<Int?>(null)
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Pink200,
                                            Pink300,
                                            Pink300
                                        )
                                    )
                                )
                        ) {
                            val gameLevel by viewModel.gameLevel.collectAsState()
                            val gameTurn by viewModel.gameTurn.collectAsState()
                            val listOfMovements = viewModel.listOfMovements

                            Column(
                                modifier = Modifier
                                    .padding(vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    ProfileItem(
                                        text = "You",
                                        avatar = R.drawable.ic_profile,
                                        taw = R.drawable.ic_circle,
                                        tawTint = Color.White
                                    )
                                    ProfileItem(
                                        text = "System",
                                        avatar = R.drawable.ic_robot,
                                        taw = R.drawable.ic_close,
                                        tawTint = Yellow200
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Yellow500)
                                            .clickable {
                                                if (listOfMovements.count { it.filled } <= 0 && gameLevel in 3..24) {
                                                    viewModel.changeLevel(gameLevel + 1)
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.KeyboardArrowUp,
                                            contentDescription = "",
                                            tint = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(140.dp, 40.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Yellow500),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        AnimatedContent(
                                            targetState = gameLevel,
                                            label = "",
                                            transitionSpec = {
                                                if (targetState > initialState) {
                                                    slideInVertically() + fadeIn() togetherWith slideOutVertically() + fadeOut()
                                                } else {
                                                    slideInVertically() + fadeIn() togetherWith slideOutVertically() + fadeOut()
                                                }.using(sizeTransform = SizeTransform(false))
                                            }
                                        ) { target ->
                                            Text(text = target.toString(), color = Color.White)
                                        }
                                    }
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Yellow500)
                                            .clickable {
                                                if (listOfMovements.count { it.filled } <= 0 && gameLevel in 4..25) {
                                                    viewModel.changeLevel(gameLevel - 1)
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.KeyboardArrowDown,
                                            contentDescription = "",
                                            tint = Color.White
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.size(16.dp))
                                Box(
                                    modifier = Modifier
                                        .size(315.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.White)
                                        .drawBehind {
                                            drawRoundRect(
                                                color = Pink500,
                                                style = Stroke(
                                                    width = 10f,
                                                    pathEffect = PathEffect.dashPathEffect(
                                                        floatArrayOf(20f, 10f), 0f
                                                    ),
                                                ),
                                                cornerRadius = CornerRadius(20.dp.toPx())
                                            )
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    LazyVerticalGrid(
                                        modifier = Modifier
                                            .fillMaxSize(0.94f)
                                            .clip(RoundedCornerShape(14.dp)),
                                        columns = GridCells.Fixed(gameLevel),
                                    ) {
                                        itemsIndexed(listOfMovements) { index, movements ->
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(Pink500)
                                                    .aspectRatio(1f)
                                                    .clickable {
                                                        if (win == null) {
                                                            viewModel.newMovement(index)
                                                        }
                                                        viewModel.checkWin(index) {
                                                            win = it
                                                        }
                                                    }
                                                    .drawBehind {
                                                        drawLine(
                                                            color = Color.White,
                                                            Offset(size.width, 15f),
                                                            Offset(size.width, size.height - 15),
                                                            7f,
                                                            pathEffect = PathEffect.dashPathEffect(
                                                                floatArrayOf(20f, 10f), 0f
                                                            )
                                                        )
                                                        drawLine(
                                                            color = Color.White,
                                                            Offset(15f, size.width),
                                                            Offset(size.height - 15, size.height),
                                                            7f,
                                                            pathEffect = PathEffect.dashPathEffect(
                                                                floatArrayOf(20f, 10f), 0f
                                                            )
                                                        )
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                val image = getTaw(movements.turn)
                                                val animationDuration = 500
                                                Column {
                                                    AnimatedVisibility(
                                                        visible = movements.filled,
                                                        enter = scaleIn(tween(animationDuration)) + fadeIn(tween(animationDuration)),
                                                        exit = scaleOut(tween(animationDuration)) + fadeOut(tween(animationDuration))
                                                    ) {
                                                        Icon(
                                                            modifier = Modifier
                                                                .size(if (gameLevel >= 7) 20.dp else 30.dp),
                                                            painter = painterResource(id = image),
                                                            contentDescription = "",
                                                            tint = if (movements.turn != 0) Color.White else Yellow500
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.size(16.dp))
                                AnimatedVisibility(
                                    visible = win == null,
                                    enter = fadeIn() + scaleIn(),
                                    exit = fadeOut() + scaleOut()
                                ) {
                                    val turnBoxColor by animateColorAsState(
                                        targetValue = if (gameTurn == 0) Pink500 else Yellow500,
                                        animationSpec = tween(500)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(140.dp,40.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(turnBoxColor),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row {
                                            AnimatedVisibility(
                                                visible = gameTurn == 0,
                                                enter = scaleIn(),
                                                exit = scaleOut()
                                            ) {
                                                Text(text = "Your turn")

                                            }
                                        }
                                        Row {
                                            AnimatedVisibility(
                                                visible = gameTurn == 1,
                                                enter = scaleIn(),
                                                exit = scaleOut()
                                            ) {
                                                Text(text = "System Turn")

                                            }
                                        }
                                    }
                                }
                                AnimatedVisibility(
                                    visible = win != null,
                                    enter = fadeIn() + scaleIn(),
                                    exit = fadeOut() + scaleOut()
                                ) {
                                    Column {
                                        Box(
                                            modifier = Modifier
                                                .size(140.dp, 40.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(if (win == 0) Pink500 else Yellow500),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(text = if (win == 0) "You Won" else "System Won")
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(140.dp, 40.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(if (win == 0) Pink500 else Yellow500)
                                                .clickable {
                                                    viewModel.resetGame()
                                                    win = null
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(text = "Reset Game")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getTaw(turn: Int?) = if (turn == 1) R.drawable.ic_close else R.drawable.ic_circle

@Composable
fun RowScope.ProfileItem(
    text: String,
    avatar: Int,
    taw: Int,
    tawTint: Color
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(
                    start = 35.dp,
                    end = 35.dp,
                    top = 40.dp,
                    bottom = 15.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = text, style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Box(
                modifier = Modifier
                    .size(65.dp, 45.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Pink500),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = taw),
                    contentDescription = "",
                    tint = tawTint
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(3.dp, Pink500, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = avatar),
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTacToeTheme {
    }
}