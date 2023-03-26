package client

import api.BookServiceGrpc.BookServiceStub
import api._
import scalapb.GeneratedMessage

import scala.concurrent.Future

sealed trait ClientRequest[A <: GeneratedMessage] {
    val responseF: BookServiceStub => Future[A]
    def response(stub: BookServiceStub): Future[A] = responseF(stub)
}

object ClientRequest {
    case class FindById(id: Int) extends ClientRequest[OptionalBook] {
        override val responseF: BookServiceStub => Future[OptionalBook] = _.findById(BookByIdRequest(id))
    }
    case class FindByTitle(title: String) extends ClientRequest[Books] {
        override val responseF: BookServiceStub => Future[Books] = _.findByTitle(BookByTitleRequest(title))
    }
    case class Put(title: String, author: Option[String], year: Option[Int]) extends ClientRequest[Book] {
        override val responseF: BookServiceStub => Future[Book] = _.put(PutBookRequest(title, author, year))
    }
    case class DeleteById(id: Int) extends ClientRequest[OptionalBook] {
        override val responseF: BookServiceStub => Future[OptionalBook] = _.deleteById(BookByIdRequest(id))
    }
    case class ManyBooks(pageNumber: Option[Int], pageLength: Option[Int]) extends ClientRequest[Books] {
        private val pagingRequest = (n: Int, l: Int) => PagingRequest(pageNumber = n, pageLength = l)
        override val responseF: BookServiceStub => Future[Books] =
            _.manyBooks(OptionalPagingRequest((pageNumber zip pageLength) map pagingRequest.tupled))
    }
}