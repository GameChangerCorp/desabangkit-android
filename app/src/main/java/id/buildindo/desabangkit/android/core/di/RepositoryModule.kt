package id.buildindo.desabangkit.android.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.buildindo.desabangkit.android.core.data.AuthRepository
import id.buildindo.desabangkit.android.core.data.ProductRepository
import id.buildindo.desabangkit.android.core.domain.repository.IAuthRepository
import id.buildindo.desabangkit.android.core.domain.repository.IProductRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideAuthRepository(authRepository: AuthRepository) : IAuthRepository

    @Binds
    abstract fun provideProductRepository(productRepository: ProductRepository) : IProductRepository
}