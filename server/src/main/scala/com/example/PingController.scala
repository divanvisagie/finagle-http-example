package com.example

import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import io.swagger.models.{Info, Swagger}
import com.twitter.util.Future

class Test {

  def doThing : Future[String] = {
    Future value "This is a string"
  }
}

case class Pong(pong: String)

object FinatraSwaggerDocument extends Swagger

class PingController extends Controller with SwaggerSupport {

  FinatraSwaggerDocument.info(new Info()
      .description("Simple ping service with both get and post methods")
      .version("0.0.1")
      .title("Finagle Http Example Server")
  )

  implicit protected val swagger = FinatraSwaggerDocument
  get("/ping",
    swagger { o =>
      o.summary("Get service for pings")
        .tag("Ping")
        .responseWith[Pong](200, "the pong message payload")
        .responseWith(404, "there is no pong")
    }
  ) { request: Request =>
    info("get ping")
    Future.value(Pong("pong"))
  }

  post("/ping",
    swagger { o =>
      o.summary("Post service for ping")
        .tag("Ping")
        .bodyParam[String]("a stringy payload")
        .responseWith[Pong](200, "the pong message payload")
        .responseWith(420, "you need to enhance your calm")
        .responseWith(404, "something is not right")
    }
  ) { request: Request =>
    info("post ping")
    val contentString = request.getContentString()
    Future.value(Pong(s"$contentString ping"))
  }

  
}
