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

    fun getMessageList(): ArrayList<Message>{
        return arrayListOf(
            Message(
                1,
                "Hai, dengan sendiko disini ada yang bisa saya bantu?",
                "07:00",
                "AWAY",
                true
            ),
            Message(
                1,
                "Halo, Saya haris. saya ingin bertanya tentang jumlah nutrisi yang cocok untuk ternak saya",
                "07:01",
                "HOME",
                false
            ),
        )
    }

}