import com.twitter.finagle.Http
import com.twitter.finagle.http.{Method, Request, Version}
import com.twitter.util.{Await, Future}

class HttpClient {
  lazy val client = Http.client
    .withLabel("finagle-client")
    .newService("localhost:9999")

  private def requestHandler(request: Request) = client(request).map(_.contentString)

  def get(url: String): Future[String] = {
    val request = Request(Version.Http11, Method.Get, url)
    requestHandler(request)
  }

  def post(url: String, payload: String) = {
    val request = Request(Version.Http11, Method.Post, url)
    request.setContentString(payload)
    requestHandler(request)
  }
}
object HttpClient {
  def apply() = new HttpClient()
}


object FinagleClient {

  val httpClient = HttpClient()

  def main(args: Array[String]) {
    //do get request
    Await.ready(httpClient get "/ping") onSuccess { responseString =>
      println(s"got: $responseString from get /ping")
    }

    //do post request
    Await.ready(httpClient post ("/ping","myPayload")) onSuccess { responseString =>
      println(s"got: $responseString from post /ping")
    }

  }
}
