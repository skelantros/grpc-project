package server

import scalikejdbc._

object BookQueries {
    case class Paging(pageNumber: Int, pageLength: Int)

    private def toBookEntity(rs: WrappedResultSet) =
        BookEntity(Some(rs.int("id")), rs.string("title"), rs.stringOpt("author"), rs.intOpt("_year"))

    def all(paging: Option[Paging]): Seq[BookEntity] = {
        val query = paging match {
            case Some(Paging(number, length)) => sql"select * from book limit $length offset ${(number - 1) * length}"
            case None => sql"select * from book"
        }
        DB readOnly { implicit session =>
            query.map(toBookEntity).toList.apply()
        }
    }

    def findById(id: Int): Option[BookEntity] = DB readOnly { implicit session =>
        sql"select * from book where id = ${id}".map(toBookEntity).single.apply()
    }

    def findByTitle(title: String): Seq[BookEntity] = DB readOnly { implicit session =>
        sql"select * from book where title = $title".map(toBookEntity).toList.apply()
    }

    def put(bookEntity: BookEntity): BookEntity = {
        val key = DB localTx { implicit session =>
            sql"insert into book(title, author, _year) values (${bookEntity.title}, ${bookEntity.author}, ${bookEntity.year})".updateAndReturnGeneratedKey("id").apply()
        }

        findById(key.toInt).get
    }

    def delete(id: Int): Option[BookEntity] = {
        val value = findById(id)
        DB localTx { implicit session =>
            sql"delete from book where id = $id".update.apply()
        }
        value
    }
}
