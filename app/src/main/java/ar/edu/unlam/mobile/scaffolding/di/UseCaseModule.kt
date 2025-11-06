package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.domain.repository.NewsRepository
import ar.edu.unlam.mobile.scaffolding.domain.repository.ProfessionalsRepository
import ar.edu.unlam.mobile.scaffolding.domain.usecase.newUseCase.GetNewsUseCase
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsByIdUseCase
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetNewsUseCase(repository: NewsRepository): GetNewsUseCase = GetNewsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetProfessionalByIdUseCase(repository: ProfessionalsRepository): GetProfessionalsByIdUseCase =
        GetProfessionalsByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideGetProfessionalsUseCase(repository: ProfessionalsRepository): GetProfessionalsUseCase = GetProfessionalsUseCase(repository)
}
