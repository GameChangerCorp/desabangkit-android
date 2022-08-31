package id.buildindo.desabangkit.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import id.buildindo.desabangkit.android.core.domain.usecase.AuthUseCase
import id.buildindo.desabangkit.android.core.domain.usecase.IAuthUseCase
import id.buildindo.desabangkit.android.core.domain.usecase.IProductUseCase
import id.buildindo.desabangkit.android.core.domain.usecase.ProductUseCase

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideAuthUseCase(useCase : AuthUseCase) : IAuthUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideProductUseCase(useCase: ProductUseCase) : IProductUseCase
}