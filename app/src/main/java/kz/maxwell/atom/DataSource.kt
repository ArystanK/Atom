package kz.maxwell.atom

import kz.maxwell.atom.models.Advice
import kz.maxwell.atom.models.Purchase

// add to firebase
class DataSource {

    companion object {
        fun createDataSetPurchase(): ArrayList<Purchase> {
            val list = ArrayList<Purchase>()
            list.add(
                Purchase(
                    "Zara",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/Zara_Logo.svg/1920px-Zara_Logo.svg.png",
                    "Купон на 25%",
                    4.00f
                )
            )
            list.add(
                Purchase(
                    "NETFLIX",
                    "https://yt3.ggpht.com/a/AATXAJyzyrPJMwSCUxtTlY-MQ9sEqX8XHm8MYq4yr7e6Gw=s100-c-k-c0xffffffff-no-rj-mo",
                    "Подписка на 3 месяца",
                    10.00f
                )
            )
            list.add(
                Purchase(
                    "YANDEX MUSIC",
                    "https://i.ytimg.com/vi/rK5A1vs6e7Q/maxresdefault.jpg",
                    "Подписка на 2 месяца",
                    5.50f
                )
            )
            list.add(
                Purchase(
                    "GREEN BAG",
                    "https://img1.wbstatic.net/big/new/9200000/9207539-1.jpg",
                    "Эко-сумка",
                    3.00f
                )
            )
            return list
        }

        fun createDataSetAdvice(): ArrayList<Advice> {
            val list = ArrayList<Advice>()
            list.add(
                Advice(
                    "Сжигание мусора",
                    "https://greenglade-snt.ru/UserImages/Uploads/images/burning_1.jpg",
                    "Мусоросжигание - это экологически опасный способ обращения с отходами"
                )
            )
            list.add(
                Advice(
                    "Этикетки и крышки",
                    "https://scontent-atl3-2.cdninstagram.com/v/t51.2885-15/sh0.08/e35/c0.135.1080.1080a/s640x640/118798463_240423367325277_8755346325893632153_n.jpg?_nc_ht=scontent-atl3-2.cdninstagram.com&_nc_cat=109&_nc_ohc=yggqhqyOsuIAX-9WbJF&oh=2e4f7b048ffed3f9129bfef3fa034e62&oe=5F86C47B",
                    "Почти все этикетки для бутылок изготавливаются из мягкого пластика"
                )
            )
            return list
        }
    }
}