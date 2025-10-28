package ar.edu.unlam.mobile.scaffolding.di

import android.content.Context
import androidx.room.Room
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AppDataBase
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.PersonaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.junit.runner.manipulation.Ordering
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun priovideAppDatabase(@ApplicationContext appContext: Context): AppDataBase{
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "my_app_db"
        ).build()
    }
    @Provides
    fun providePersonaDao(db: AppDataBase): PersonaDao{
        return db.personaDao()
    }

}