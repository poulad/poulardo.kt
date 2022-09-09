package io.github.poulad.someapp.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Author(
    @Id @GeneratedValue var id: Long? = null,
    var login: String,
    var firstname: String,
    var lastname: String,
)
