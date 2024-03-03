package com.bharath.a12weekpreparation.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bharath.a12weekpreparation.data.ProblemEntity
import com.bharath.a12weekpreparation.data.WeekConstants

val tabsList = listOf(
    WeekConstants.Week1,
    WeekConstants.Week2,
    WeekConstants.Week3,
    WeekConstants.Week4,
    WeekConstants.Week5,
    WeekConstants.Week6,
    WeekConstants.Week7,
    WeekConstants.Week8,
    WeekConstants.Week9,
    WeekConstants.Week10,
    WeekConstants.Week11,
    WeekConstants.Week12,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navHostController: NavHostController,

    ) {
    val tabsList = remember {
        tabsList
    }
    val context = LocalContext.current
    val viewModel = hiltViewModel<MainViewModel>()
    val tabIndex by viewModel.tabIndex.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true, block = {
        viewModel.insertDataFromCsv(context)
    })

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize(),
        topBar = {

            Column {


                TopAppBar(title = {
                    Text(text = "12 Week Preparation")
                })
                Divider(
                    thickness = Dp.Hairline

                )
            }
        }

    ) { innerPad ->


        Column(
            modifier = Modifier
                .padding(innerPad)
                .padding(8.dp)
        ) {


            ScrollableTabRow(
                selectedTabIndex = tabIndex,
                edgePadding = 5.dp,
                containerColor = MaterialTheme.colorScheme.surface, divider = {

                }
            ) {
                tabsList.forEachIndexed { index, tab ->

                    Tab(selected = index == tabIndex, onClick = {

                        viewModel.updateIndex(index)
                        viewModel.getProblemsByWeek(
                            tabsList[index]
                        )
                    }) {
                        Text(
                            text = tab,
                            fontWeight = FontWeight.SemiBold,
                            color = if (index == tabIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(
                                0.7f
                            ),
                            modifier = Modifier.padding(8.dp)

                        )
                    }
                }
            }

            val practiceList by viewModel.problemsListPractice.collectAsStateWithLifecycle()
            val studyList by viewModel.problemsListStudy.collectAsStateWithLifecycle()

            val onCheckChange: (checked: Boolean, entity: ProblemEntity) -> Unit =
                remember {
                    return@remember { bool, entity ->
                        viewModel.onCheckedChange(entity, bool)
                    }
                }
            val mod = Modifier
                .padding(16.dp)
                .fillMaxWidth()
            LazyColumn(content = {
                item {
                    if (practiceList.isNotEmpty()) {
                        val item = remember {
                            derivedStateOf {

                                practiceList.first()
                            }
                        }
                        Text(
                            text = item.value.problemTopic + " " + item.value.problemSubTopic,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = mod

                        )
                    }
                }
                item {
                    Text(text = "Study", fontWeight = FontWeight.Bold, modifier = mod)
                }
                items(studyList, key = {
                    ("${it.problemId}${it.isCompleted}")
                }) { entity ->
                    val animated = remember {
                        Animatable(0.65f)
                    }
                    LaunchedEffect(key1 = true, block = {
                        animated.animateTo(1f, tween(400))
                    })
                    ProblemCard(problemEntity = entity, modifier = mod.graphicsLayer {
                        this.scaleY = animated.value
                        this.scaleX = animated.value
                    }) {
                        navHostController.navigate("Problem" + "/${entity.problemId}")
                    }
                }
                item {
                    Text(
                        text = "Practice",
                        fontWeight = FontWeight.Bold,
                        modifier = mod
                    )
                }

                items(practiceList, key = {
                    ("${it.problemId}${it.isCompleted}")
                }) { entity ->
                    val animated = remember {
                        Animatable(0.65f)
                    }
                    LaunchedEffect(key1 = true, block = {
                        animated.animateTo(1f, tween(300))
                    })
                    ProblemCard(
                        problemEntity = entity, modifier = mod.graphicsLayer {
                            this.scaleY = animated.value
                            this.scaleX = animated.value
                        }, background = MaterialTheme.colorScheme.tertiary.copy(0.6f)
                    ) {
                        navHostController.navigate("Problem" + "/${entity.problemId}")

                    }
                }
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProblemCard(
    problemEntity: ProblemEntity,
    modifier: Modifier,
    background: Color = MaterialTheme.colorScheme.secondaryContainer.copy(0.7f),
    onClick: () -> Unit,
) {

    Card(
        modifier = modifier.alpha(
            if (problemEntity.isCompleted) 0.3f else 1f
        ),
        colors = CardDefaults.cardColors(
            containerColor = background
        ),
        onClick = onClick
    ) {

        Row(
            modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = problemEntity.problemName,
                maxLines = 1,
                fontWeight = FontWeight.Medium,
                textDecoration = if (problemEntity.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.basicMarquee()
            )


        }
    }

}