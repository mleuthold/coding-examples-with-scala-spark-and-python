import scala.sys.process._

object NetcatServer {
  def async(fun: => Unit) {
    val t = new Thread(new Runnable { def run { fun } })
    t.start()
  }

  //----------------------------------------------------------------------------

  def serveRaw(port: Int, lines: Seq[String]) {
    val raw = lines.mkString("\r\n")
    (Seq("echo", "-n", raw) #| Seq("sh", "-c", "nc -l " + port)).!
  }

  def serveContent(port: Int, contentType: String, content: String) {
    val contentLength = content.getBytes.length
    serveRaw(port, Seq(
      "HTTP/1.1 200 OK",
      s"Content-Type: $contentType",
      s"Content-Length: $contentLength",
      "",
      content
    ))
  }

  def asyncServeRaw(port: Int, lines: Seq[String]) {
    async { serveRaw(port, lines) }
  }

  def asyncServeContent(port: Int, contentType: String, content: String) {
    async { serveContent(port, contentType, content) }
  }

  //----------------------------------------------------------------------------

  def requestRaw(host: String, port: Int, lines: Seq[String]): String = {
    val raw = lines.mkString("", "\r\n", "\r\n\r\n")
    // "-i 1" delays 1s, slowering the tests.
    // But without it the result will be empty.
    (Seq("echo", "-n", raw) #| s"nc -i 1 $host $port").!!
  }

  def get(host: String, port: Int, path: String): String = {
    requestRaw(host, port, Seq(
      s"GET $path HTTP/1.1",
      s"Host: $host:$port"
    ))
  }
}