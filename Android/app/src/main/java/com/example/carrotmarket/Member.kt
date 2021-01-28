package com.example.carrotmarket

class Member(email: String, password: String, name: String, nickname: String, /*photo,*/ iscorrectphonenumber: Boolean, town: String) {
    var email: String = "";
    var password: String = "";
    var name: String = "";
    var nickname: String = "";
    var photo = "";
    var iscorrectphonenumber: Boolean = false;
    var town: String = "";

    init {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        /*this.photo = photo;*/
        this.iscorrectphonenumber = iscorrectphonenumber;
        this.town = town;
    }
}
