package server

import api._
import api.BookServiceGrpc.BookService

import scala.concurrent.{ExecutionContext, Future}

class ServiceImpl(implicit ec: ExecutionContext) extends BookService {
    override def findById(request: BookByIdRequest): Future[OptionalBook] =
        Future(OptionalBook(BookQueries.findById(request.id).flatMap(_.toBook)))

    override def findByTitle(request: BookByTitleRequest): Future[Books] =
        Future(Books(BookQueries.findByTitle(request.title).flatMap(_.toBook)))

    override def put(request: PutBookRequest): Future[Book] =
        Future(BookQueries.put(BookEntity(request)).toBook.get)

    override def deleteById(request: BookByIdRequest): Future[OptionalBook] =
        Future(OptionalBook(BookQueries.delete(request.id).flatMap(_.toBook)))

    override def manyBooks(request: OptionalPagingRequest): Future[Books] = {
        val paging = request.pagingRequest match {
            case Some(PagingRequest(pageNumber, pageLength, _)) =>
                Some(BookQueries.Paging(pageNumber, pageLength))
            case _ => None
        }
        Future(Books(BookQueries.all(paging).flatMap(_.toBook)))
    }
}