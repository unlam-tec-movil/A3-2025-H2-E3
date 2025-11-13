package ar.edu.unlam.mobile.scaffolding.di

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.data.services.LocationProvider
import ar.edu.unlam.mobile.scaffolding.data.services.VibratorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocationProvider(@ApplicationContext context: Context): LocationProvider {
        return LocationProvider(context)
    }

    @Provides
    @Singleton
    fun provideVibratorManager(@ApplicationContext context: Context): VibratorManager {
        return VibratorManager(context)
    }
}