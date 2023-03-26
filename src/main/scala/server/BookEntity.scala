package server

import api.{Book, PutBookRequest}

case class BookEntity(id: Option[Int], title: String, author: Option[String], year: Option[Int]) {
    def toBook: Option[Book] = id.map(id => Book(id, title, author, year))
}

object BookEntity {
    def apply(putRequest: PutBookRequest): BookEntity =
        BookEntity(None, putRequest.title, putRequest.author, putRequest.year)
}