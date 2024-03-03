package com.bharath.a12weekpreparation.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.a12weekpreparation.data.ProblemEntity
import com.bharath.a12weekpreparation.data.ProblemRepo
import com.bharath.a12weekpreparation.data.WeekConstants
import com.bharath.a12weekpreparation.data.datastore.DataStorePrefRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val problemRepo: ProblemRepo,
    private val dataStorePrefRepo: DataStorePrefRepo,
) : ViewModel() {
    private val _problemsListPractice = MutableStateFlow(emptyList<ProblemEntity>())
    val problemsListPractice = _problemsListPractice.asStateFlow()

    private val _problemsListStudy = MutableStateFlow(emptyList<ProblemEntity>())
    val problemsListStudy = _problemsListStudy.asStateFlow()

    private val _tabIndex = MutableStateFlow(0)
    val tabIndex = _tabIndex.asStateFlow()


    private val weekState = mutableStateOf(WeekConstants.Week1)

    init {
        viewModelScope.launch {
            dataStorePrefRepo.getTabIndex().filterNotNull().first().let {
                _tabIndex.emit(it)
                getProblemsByWeek(tabsList[it])
                //                weekState.value = tabsList[it]
            }
        }
    }

    private fun insertData(context: Context) {
        viewModelScope.launch(IO) {


            Log.i("CSV", "insertData: Inserting ")
            val inputStream = context.assets.open("studysheet.csv")
            val lines = mutableListOf<String>()
            inputStream.bufferedReader().useLines {
                lines.addAll(
                    it.toList()
                )
            }

            val resultList = mutableListOf<ProblemEntity>()
            withContext(IO) {

                lines.forEachIndexed { ind, string ->
                    if (ind > 0) {
                        val list = string.split(',')
                        val entity = ProblemEntity(
                            problemId = list[0].toInt(),
                            problemTopic = list[1],
                            problemSubTopic = list[2],
                            problemName = list[3],
                            isCompleted = false,
                            notes = "",
                            week = list[6],
                            learningType = list[7]

                        )
                        resultList.add(entity)
                    }
                }
//                val temp=lines.map {
//                    val list = it.split(',')
//                    ProblemEntity(
//                        problemId = list[0].toInt(),
//                        problemTopic = list[1],
//                        problemSubTopic = list[2],
//                        problemName = list[3],
//                        isCompleted = false,
//                        notes = "",
//                        week = list[6],
//                        learningType = list[7]
//
//                    )
//                }
            }
            problemRepo.insert(resultList)
        }


    }


    suspend fun saveTabIndex(index: Int) {
        viewModelScope.launch {

            dataStorePrefRepo.setTabIndex(index)
        }

    }

    fun onCheckedChange(entity: ProblemEntity, boolean: Boolean) {
        viewModelScope.launch(IO) {
            problemRepo.update(
                entity.copy(
                    isCompleted = boolean
                )
            )
        }
    }


    fun insertDataFromCsv(context: Context) {

        viewModelScope.launch {
            problemRepo.checkIsEmpty().filterNotNull().first().let {
                if (it == 0) {
                    insertData(context)
                }
            }
        }
    }


    fun updateIndex(index: Int) {
        viewModelScope.launch {
            _tabIndex.update { index }
            saveTabIndex(index)
        }
    }


    fun getProblemsByWeek(week: String) {


        viewModelScope.launch(IO) {
            problemRepo.getPracticeProblems(week)
                .collectLatest { list ->
                    _problemsListPractice.update {
                        list
                    }
                }
        }
        viewModelScope.launch(IO) {
            problemRepo.getStudyProblems(week).collectLatest { list ->
                _problemsListStudy.update {
                    list
                }
            }
        }
    }

}