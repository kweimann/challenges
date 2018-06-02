package kweimann.challenges.fastrouter

object FastRouter {
  import Types._

  object Types {
    type PathComponent = String
    type PathPattern = List[PathComponent]
    type Endpoint = String
  }

  class RouteNode() {
    def route(pathPattern: PathPattern): Endpoint = ???
  }
  object RouteNode {
    def apply(config: List[(PathPattern, Endpoint)]): RouteNode = ???
  }
}
