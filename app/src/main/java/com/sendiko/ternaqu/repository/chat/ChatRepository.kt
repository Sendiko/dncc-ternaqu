package com.sendiko.ternaqu.repository.chat

class ChatRepository {

    fun getChatList(): ArrayList<Chat>{
        return arrayListOf(
            Chat(
                1,
                "Haris",
                "inpo ayang",
                "12/12/12",
                "https://mylms.telkomschools.sch.id/theme/image.php/moove/core/1673197142/u/f1"
            ),
            Chat(
                2,
                "Rayya",
                "inpo inpo",
                "12/12/12",
                "https://mylms.telkomschools.sch.id/theme/image.php/moove/core/1673197142/u/f1"
            ),
            Chat(
                3,
                "Sendiko",
                "bismillah juara 1",
                "11/02/97",
                "https://mylms.telkomschools.sch.id/theme/image.php/moove/core/1673197142/u/f1"
            ),
        )
    }

}