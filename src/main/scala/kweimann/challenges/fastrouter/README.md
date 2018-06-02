# Challenge

Many popular web frameworks route incoming requests to endpoints by matching the request path against a series of regular expressions. The first regular expression that matches determines which method should handle the request. However, this becomes noticeably slow if your application needs to dispatch among dozens or hundreds of endpoints.

### Task

Write a dispatch routine whose running time does not depend linearly on the number of endpoints, but rather only on the number of components in the request path.

Your program should parse its configuration from a list of path patterns, each followed by a string token (taken to represent the name of the method that handles that endpoint). Path patterns may contain wildcards, denoted by an X. Here is a sample configuration:

```
/ rootEndpoint
/user userRootEndpoint
/user/friends userFriendsEndpoint
/user/lists userListsEndpoint
/user/X userEndpoint
/user/X/friends userFriendsEndpoint
/user/X/lists userListsEndpoint
/user/X/lists/X userListIdEndpoint
/X/friends userFriendsEndpoint
/X/lists userListsEndpoint
/settings settingsEndpoint
```

The program receives a list of request paths and should output the list of names of the methods corresponding to the matching pattern. If multiple path patterns match, you should prefer static patterns to patterns with wildcards. If no patterns match, you should output the string “404”. You can assume that path patterns will be delimited by the `/` character and that wildcards will always appear by themselves (i.e., you won’t see /foo/barX/baz). You may also assume that the input is well-formed. 

For example, given the above configuration and the following input

```
/
/user
/user/friends
/user/123
/user/123/friends
/user/123/friends/zzz
/user/friends/friends
/abc/lists
/settings
/aaa/bbb
```

your program should output

```
rootEndpoint
userRootEndpoint
userFriendsEndpoint
userEndpoint
userFriendsEndpoint
404
userFriendsEndpoint
userListsEndpoint
settingsEndpoint
404
```

# Solution

### Description

The router is organized in a tree structure such that every node in a tree handles a single path component. 

First, for each line in the configuration, the path pattern is split into a list of path components and route nodes are recursively created or updated (if such node already exists) for every path component in the list. The last node in the path assumes the role of an endpoint.

Routing is a simple tree traversal ending with an endpoint if such endpoint exists or fallback endpoint otherwise.

### Analysis

##### Complexity

Assuming the number of path components in a path pattern is bound by `k`:

1. creating router: `O(n*k)`
2. routing: `O(k)` However in some rare cases complexity may rise to `O(n*k)`. This complexity trap occurs if every path in the configuration potentially qualifies for given request but eventually results in a fallback e.g. consider following configuration: 
    * there are `n` path patterns all of length `n` such that for each path `n_i` the `i`th component is a wildcard and the remaining static path components are the same for every path.
    * Now consider a path request of length `n+1` such that first `n` components are equal to the static path components found in the configuration.
    * During the lookup, the traversal would end at the last (`n`th) static component resulting in a fallback. Then it would enter the wildcard node and perform lookup from there until `n`th node is reached, again resulting in a fallback and going back to the previous node. And so on until the last wildcard node which would traverse through all of its `n-1` children containing static path components resulting in the final fallback and thus end of the lookup.
    * In this case the complexity would be `O(n^2)` since `k=n`.

##### Memory

In worst case where each path component is unique:

1. router: `O(n*k)`

### Discussion

The above complexity analysis assumes that Map lookup is `O(1)`. However, due to caching, the lookup speed highly depends on the size of the Map.

Furthermore, since endpoint lookup is recursive, it may lead to a stack overflow exception when the router is exceptionally deep.

Configuration parsing has been implemented without worrying too much about the complexity since it is assumed that once the configuration has been parsed the total cost of endpoint lookups will outweigh the cost of creating the router by a huge margin.

Finally, the dangerous complexity trap outlined in previous chapter could be alleviated by careful insertion of wildcards in the configuration.