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

### Analysis

### Discussion