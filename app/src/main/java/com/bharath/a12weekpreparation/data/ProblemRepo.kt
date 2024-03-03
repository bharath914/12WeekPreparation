package com.bharath.a12weekpreparation.data

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProblemRepo @Inject constructor(
    private val problemsDao: ProblemsDao,
) {
    suspend fun insert(list: List<ProblemEntity>) {
        withContext(IO) {
            problemsDao.insert(list)
        }
    }

    suspend fun delete(entity: ProblemEntity) {
        withContext(IO) {
            problemsDao.delete(entity)
        }
    }

    suspend fun update(entity: ProblemEntity) {
        withContext(IO) {
            problemsDao.update(entity)
        }
    }

    suspend fun getStudyProblems(week: String): Flow<List<ProblemEntity>> {
        return withContext(IO) {
            problemsDao.getStudyProblems(week)
        }
    }

    suspend fun getPracticeProblems(week: String): Flow<List<ProblemEntity>> {
        return withContext(IO) {
            problemsDao.getPracticeProblems(week)
        }
    }

    suspend fun checkIsEmpty(): Flow<Int> {
        return withContext(IO) {
            problemsDao.checkIsEmpty()
        }
    }

    suspend fun getProblemDetails(id: Int): Flow<ProblemEntity> {
        return withContext(IO) {
            problemsDao.getProblemById(id)
        }
    }

}