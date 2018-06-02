package kweimann.challenges.fastrouter

import org.scalatest.{FlatSpec, Matchers}

class FastRouterTest extends FlatSpec with Matchers {
  import FastRouter.Types.Endpoint

  it should "route based on configuration" in {
    val config = List(
      ("/", "rootEndpoint"),
      ("/user", "userRootEndpoint"),
      ("/user/friends", "userFriendsEndpoint"),
      ("/user/lists", "userListsEndpoint"),
      ("/user/X", "userEndpoint"),
      ("/user/X/friends", "userFriendsEndpoint"),
      ("/user/X/lists", "userListsEndpoint"),
      ("/user/X/lists/X", "userListIdEndpoint"),
      ("/X/friends", "userFriendsEndpoint"),
      ("/X/lists", "userListsEndpoint"),
      ("/settings", "settingsEndpoint")
    ).map {
      case (pathPattern, endpoint) =>
        (pathPattern.split("/").toList, endpoint)
    }

    val input = List(
      "/",
      "/user",
      "/user/friends",
      "/user/123",
      "/user/123/friends",
      "/user/123/friends/zzz",
      "/user/friends/friends",
      "/abc/lists",
      "/settings",
      "/aaa/bbb",
    ).map(_.split("/").toList)

    val output = List(
      "rootEndpoint",
      "userRootEndpoint",
      "userFriendsEndpoint",
      "userEndpoint",
      "userFriendsEndpoint",
      "404",
      "userFriendsEndpoint",
      "userListsEndpoint",
      "settingsEndpoint",
      "404"
    )

    implicit val fallback: Endpoint = "404"
    val wildcardComponent = "X"
    val router = FastRouter.RouteNode(config)(wildcardComponent)

    input.map(router.route) shouldEqual output
  }
}
