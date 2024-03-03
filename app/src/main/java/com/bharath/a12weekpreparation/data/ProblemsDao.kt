package com.bharath.a12weekpreparation.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProblemsDao {

    @Upsert(entity = ProblemEntity::class)
    suspend fun insert(list: List<ProblemEntity>)

    @Query("select * from ProblemEntity where problemId =:id limit 1")
    fun getProblemById(id: Int): Flow<ProblemEntity>

    @Delete(entity = ProblemEntity::class)
    suspend fun delete(entity: ProblemEntity)

    @Update(entity = ProblemEntity::class)
    suspend fun update(entity: ProblemEntity)

    @Query("select count(*) from ProblemEntity")
    fun checkIsEmpty(): Flow<Int>

    @Query("select * from ProblemEntity where learningType = '$Study' and week =:week ")
    fun getStudyProblems(week: String): Flow<List<ProblemEntity>>

    @Query("select * from ProblemEntity where learningType = '$Practice' and week =:week")
    fun getPracticeProblems(week: String): Flow<List<ProblemEntity>>

}