import com.twitter.finagle.Http
import com.twitter.finagle._
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.tracing._
import com.twitter.finagle.http.{Method, Request, Version}
import com.twitter.util.{Await, Future}

class HttpClient {
  lazy val client = Http.client
    .withLabel("finagle-client")
    .newService("localhost:9999")

  def get(url: String): Future[String] = {
    val request = Request(Version.Http11, Method.Get, url)
    client(request) map { response =>
      response.contentString
    }
  }
}
object HttpClient {
  def apply() = new HttpClient()
}


object FinagleClient {

  val httpClient = HttpClient()

  def main(args: Array[String]) {
    //do stuff
    Await.ready(httpClient get "/ping") onSuccess { responseString =>
      println(s"got: $responseString from get /ping")
    }

  }
}
