package com.example.urwood.repository.model

object User {

    data class UserAuth(
        var email: String = "",
        var password: String = ""
    )

    data class UserData(
        var email: String = "",
        var name: String = "",
        var phoneNumber: String = ""
    ) {
        constructor(email: String) : this(email, "", "")
    }

}