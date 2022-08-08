package id.buildindo.desabangkit.android.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.buildindo.desabangkit.android.core.data.AuthRepository
import id.buildindo.desabangkit.android.core.domain.repository.IAuthRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(authRepository: AuthRepository) : IAuthRepository
}