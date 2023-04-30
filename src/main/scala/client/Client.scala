package client

import api._
import client.ClientRequest._
import io.grpc.ManagedChannelBuilder
import server.Server

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Client extends App {
    val strRequest = args(0)
    val clientRequest = strRequest match {
        case "findById" =>
            FindById(args(1).toInt)
        case "findByTitle" =>
            FindByTitle(args(1))
        case "put" =>
            Put(args(1), args.lift(2), args.lift(3).map(_.toInt))
        case "deleteById" =>
            DeleteById(args(1).toInt)
        case "many" =>
            ManyBooks(args.lift(1).map(_.toInt), args.lift(2).map(_.toInt))
        case _ =>
            println(s"Unknown command: $strRequest")
            sys.exit()
    }

    val channel = ManagedChannelBuilder.forAddress("skel-grpc-example.onrender.com", 7352).build()
    val stub = BookServiceGrpc.stub(channel)

    clientRequest.response(stub) onComplete {
        case Success(x) => println(s"Request result: $x")
        case Failure(t) => println(s"An error occurred during request: $t"); t.printStackTrace()
    }
}
