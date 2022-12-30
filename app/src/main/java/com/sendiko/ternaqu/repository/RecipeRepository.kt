package com.sendiko.ternaqu.repository

import com.sendiko.ternaqu.repository.model.Recipe

class RecipeRepository {
    fun getRecipeList() : ArrayList<Recipe> {
        return arrayListOf(
            Recipe(
                1,
                "Recipe 1",
                "benefits one",
                "this and that",
                "step 1, 2, and 3",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmGqdd03oMQCUuePwFyXPexg_AtcVefshUgA&usqp=CAU"
            ),
            Recipe(
                2,
                "Recipe 2",
                "benefits two",
                "this and that",
                "step 1, 2, and 3",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTA5hBXAEOBbkYC2_ZWNR5GcmPBZJq_CerHpA&usqp=CAU"
            ),
            Recipe(
                3,
                "Recipe 3",
                "benefits three",
                "this and that",
                "step 1, 2, and 3",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmZsC-v1rhdT-7lEhp_GcWYpi8E_6BCTQZCQ&usqp=CAU"
            ),
        )
    }
}