package com.bharath.a12weekpreparation.presentation


import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.bharath.a12weekpreparation.data.ProblemEntity
import com.bharath.a12weekpreparation.data.ProblemRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemViewModel @Inject constructor(
    private val problemRepo: ProblemRepo,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = mutableIntStateOf(0)

    private val _problem = MutableStateFlow(ProblemEntity())
    val problem = _problem.asStateFlow()

    private val _problemDesc = MutableStateFlow("")
    val problemDesc = _problemDesc.asStateFlow()

    init {
        savedStateHandle.get<String>("id")?.let {
            id.intValue = it.toInt()
        }
    }

    fun updateDesc(text: String) {
        _problemDesc.update {
            text
        }
    }

    fun updateProblem(navHostController: NavHostController, completed: Boolean) {
        viewModelScope.launch(IO) {
            problemRepo.update(
                _problem.value.copy(
                    isCompleted = completed,
                    notes = _problemDesc.value
                )
            )
        }.invokeOnCompletion {
            viewModelScope.launch(Main) {
                navHostController.navigateUp()
            }
        }
    }

    fun getProblem() {
        viewModelScope.launch(IO) {

            problemRepo.getProblemDetails(id.intValue).collectLatest { entity ->
                _problem.emit(entity)
                _problemDesc.update {
                    entity.notes
                }
            }
        }
    }
}