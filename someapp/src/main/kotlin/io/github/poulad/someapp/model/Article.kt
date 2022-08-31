package io.github.poulad.someapp.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Article(
    @Id @GeneratedValue var id: Long? = null,
    var title: String,
    var headline: String,
    var content: String,
    @ManyToOne var author: Author,
    var addedAt: LocalDateTime = LocalDateTime.now(),
)
