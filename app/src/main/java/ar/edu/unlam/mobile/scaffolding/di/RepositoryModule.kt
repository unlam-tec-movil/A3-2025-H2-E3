package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.data.repositories.NewsRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.data.repositories.ProfessionalsRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.data.repositories.ReviewsRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.domain.repository.NewsRepository
import ar.edu.unlam.mobile.scaffolding.domain.repository.ProfessionalsRepository
import ar.edu.unlam.mobile.scaffolding.domain.repository.ReviewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository

    @Binds
    @Singleton
    fun bindProfessionalsRepository(impl: ProfessionalsRepositoryImpl): ProfessionalsRepository

    @Binds
    @Singleton
    fun bindReviewsRepository(impl: ReviewsRepositoryImpl): ReviewsRepository
}
