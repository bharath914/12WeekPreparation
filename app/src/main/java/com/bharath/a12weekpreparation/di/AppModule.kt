package com.bharath.a12weekpreparation.di


import android.app.Application
import androidx.room.Room
import com.bharath.a12weekpreparation.data.MyDataBase
import com.bharath.a12weekpreparation.data.ProblemRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): MyDataBase {
        return Room.databaseBuilder(
            application,
            MyDataBase::class.java,
            "Problems Database"
        ).build()
    }


    @Provides
    @Singleton
    fun provideProblemRepo(dataBase: MyDataBase): ProblemRepo {
        return ProblemRepo(dataBase.dao)
    }
}