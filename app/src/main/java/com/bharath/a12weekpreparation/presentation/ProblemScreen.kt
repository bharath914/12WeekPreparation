package com.bharath.a12weekpreparation.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemScreen(
    navHostController: NavHostController,
) {
    val viewModel = hiltViewModel<ProblemViewModel>()
    LaunchedEffect(key1 = true, block = {
        viewModel.getProblem()
    })
    val problem by viewModel.problem.collectAsStateWithLifecycle()
    val notes by viewModel.problemDesc.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = problem.problemSubTopic,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }, actions = {
                IconButton(onClick = {
                    viewModel.updateProblem(navHostController, false)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear, contentDescription = null
                    )
                }
                IconButton(onClick = {
                    viewModel.updateProblem(navHostController, true)
                }, modifier = Modifier.padding(end = 16.dp)) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                }
            }
            )
        }
    ) { innerPad ->

        Column(
            modifier = Modifier
                .padding(innerPad)
                .padding(16.dp)
                .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(text = buildAnnotatedString {
                append("Problem : ")
                append(
                    problem.problemName
                )
            }
            )
            Text(text = buildAnnotatedString {
                append("Data Structure : ")
                append(problem.problemTopic)
            })
            Text(text = buildAnnotatedString {
                append("Technique : ")
                append(problem.problemSubTopic)
            })
            TextField(
                value = notes,
                onValueChange = viewModel::updateDesc,
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.surface
                ),
                placeholder = {
                    Text(text = "Write Notes here...........")
                }
            )
        }
    }

}