package com.bharath.a12weekpreparation.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
@Keep
data class ProblemEntity(
    @PrimaryKey
    val problemId: Int = 0,
    val problemTopic: String = "",
    val problemSubTopic: String = "",
    val problemName: String = "",
    val isCompleted: Boolean = false,
    val notes: String = "",
    val week: String = "",
    val learningType: String = "",
)

const val Practice = "Practice"
const val Study = "Study"

object WeekConstants {
    const val Week1 = "Week1"
    const val Week2 = "Week2"
    const val Week3 = "Week3"
    const val Week4 = "Week4"
    const val Week5 = "Week5"
    const val Week6 = "Week6"
    const val Week7 = "Week7"
    const val Week8 = "Week8"
    const val Week9 = "Week9"
    const val Week10 = "Week10"
    const val Week11 = "Week11"
    const val Week12 = "Week12"
}