package com.sendiko.ternaqu.repository.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sendiko.ternaqu.repository.forum.ForumViewModel
import com.sendiko.ternaqu.repository.product.ProductViewModel
import com.sendiko.ternaqu.repository.recipe.RecipeViewModel
import com.sendiko.ternaqu.repository.user.UserViewModel

class ViewModelFactory private constructor(private val app: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(app: Application): ViewModelFactory {
            when (INSTANCE) {
                null -> {
                    synchronized(ViewModelFactory::class.java) {
                        INSTANCE = ViewModelFactory(app)
                    }
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(app) as T
            modelClass.isAssignableFrom(RecipeViewModel::class.java) -> RecipeViewModel(app) as T
            modelClass.isAssignableFrom(ProductViewModel::class.java) -> ProductViewModel(app) as T
            modelClass.isAssignableFrom(ForumViewModel::class.java) -> ForumViewModel(app) as T
            else -> throw IllegalArgumentException("Unknown model class : " + modelClass.name)
        }
    }

}