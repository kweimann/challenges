package kweimann.challenges.fastrouter

object FastRouter {
  import Types._

  object Types {
    type PathComponent = String
    type PathPattern = List[PathComponent]
    type Endpoint = String
  }

  class RouteNode private(val endpoint: Option[Endpoint],
                          val routes: Map[PathComponent, RouteNode],
                          val wildcard: Option[RouteNode]) {
    def route(pathPattern: PathPattern)(implicit fallback: Endpoint): Endpoint = pathPattern match {
      case pathComponent :: rest =>
        // first look for the endpoint in the static components then test the wildcard and
        // finally if no endpoint has been found return the fallback endpoint
        routes
          .get(pathComponent)
          .map(_.route(rest))
          // ignore fallback before the wildcard has been checked
          .filterNot(_ == fallback)
          .orElse(wildcard.map(_.route(rest)))
          .getOrElse(fallback)
      case _ =>
        // path end reached so return the endpoint if available or the fallback endpoint otherwise
        endpoint.getOrElse(fallback)
    }

    private def updated(route: (PathComponent, RouteNode)): RouteNode =
      new RouteNode(endpoint, routes.updated(route._1, route._2), wildcard)

    private def updated(endpoint: Endpoint): RouteNode = new RouteNode(Some(endpoint), routes, wildcard)

    private def updated(wildcard: RouteNode): RouteNode = new RouteNode(endpoint, routes, Some(wildcard))
  }
  object RouteNode {
    private def empty(): RouteNode = new RouteNode(None, Map.empty, None)

    def apply(config: List[(PathPattern, Endpoint)])(wildCardComponent: PathComponent): RouteNode = {
      // traverses the path pattern creating / updating nodes on the way
      def updateRouter(router: RouteNode, pathPattern: PathPattern, endpoint: Endpoint): RouteNode = {
        pathPattern match {
          // current path component is a wildcard
          case pathComponent :: rest if pathComponent == wildCardComponent =>
            router.updated(updateRouter(router.wildcard.getOrElse(RouteNode.empty()), rest, endpoint))
          // current path component is static
          case pathComponent :: rest =>
            router.updated((
              pathComponent,
              updateRouter(router.routes.getOrElse(pathComponent, RouteNode.empty()), rest, endpoint)
            ))
          // end of path reached create an endpoint
          case _ =>
            router.updated(endpoint)
        }
      }

      // merge all (path, endpoint) pairs into a router
      config
        .foldLeft(RouteNode.empty()) {
          case (router, (pathPattern, endpoint)) =>
            updateRouter(router, pathPattern, endpoint)
        }
    }
  }
}
