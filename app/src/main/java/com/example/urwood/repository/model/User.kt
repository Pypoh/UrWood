package com.example.urwood.repository.model

object User {

    data class UserAuth(
        var email: String = "",
        var password: String = ""
    )

}