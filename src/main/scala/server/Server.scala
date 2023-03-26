package server

import api.BookServiceGrpc
import io.grpc.{ServerBuilder, Server => GrpcServer}
import scalikejdbc.config.DBs

object Server extends App {
    private implicit val ec =
        scala.concurrent.ExecutionContext.global

    DBs.setup()

    private val server: GrpcServer =
        ServerBuilder.forPort(8080).addService(BookServiceGrpc.bindService(new ServiceImpl, ec)).build().start()

    sys.addShutdownHook {
        server.shutdown()
    }

    server.awaitTermination()
}