package com.sendiko.ternaqu.repository.chat

class ChatRepository {

    fun getChatList(): ArrayList<Chat>{
        return arrayListOf(
            Chat(
                1,
                "Ratu rayya",
                "Hai, Terimakasih telah menghubungi saya...",
                "12/12/12",
                "https://mylms.telkomschools.sch.id/theme/image.php/moove/core/1673197142/u/f1"
            )
        )
    }

    fun getMessageList(): ArrayList<Message>{
        return arrayListOf(
            Message(
                1,
                "Hai, Terimakasih telah menghubungi saya ada yang bisa saya bantu?",
                "07:00",
                "AWAY",
                true
            ),
            Message(
                1,
                "Halo, Saya Sendiko. saya ingin bertanya tentang jumlah nutrisi yang cocok untuk ternak saya",
                "07:01",
                "HOME",
                false
            ),
        )
    }

}