# Description
A simple client-server gRPC application written in Scala with ScalaPB library.

# Usage
1. Clone this repository.
2. Install Java & SBT.
3. Run a server: `sbt "runMain server.Server"`
4. Run a client: `sbt "runMain client.Client (client command)"`

## Available client commands
- `findById (id)` - finds a book by its id.
- `findByTitle (title)` - finds books by title.
- `all [pageLength] [pageNumber]` - returns a list of books with optional paging. If `pageLength` or `pageNumber` is not set, a list of all books will be returned.
- `put (title) [author] [year]` - inserts a new book in the database & returns a created instance.
- `delete (id)` - deletes a book from the databases by id if it exists. Returns a deleted book.