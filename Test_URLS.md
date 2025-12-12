POST http://localhost:8080/users/create?name=Anvesh
POST http://localhost:8080/users/create?name=LSiddhu
POST http://localhost:8080/users/create?name=Priyanshu
POST http://localhost:8080/users/create?name=Shakti
POST http://localhost:8080/users/create?name=Lisa



POST http://localhost:8080/users/1/follow/2
POST http://localhost:8080/users/1/follow/3
POST http://localhost:8080/users/1/follow/4
POST http://localhost:8080/users/1/follow/5
POST http://localhost:8080/users/5/follow/2
POST http://localhost:8080/users/5/follow/3
POST http://localhost:8080/users/4/follow/1



POST http://localhost:8080/users/1/unfollow/2





GET http://localhost:8080/users/1/following
GET http://localhost:8080/users/3/followers


POST http://localhost:8080/posts/create?userId=1&message=I Dont depend on last minute
POST http://localhost:8080/posts/create?userId=2&message= Trust the seniors
POST http://localhost:8080/posts/create?userId=3&message=Accidentally Be friendly with neighbours

GET http://localhost:8080/posts/feed/1


GET http://localhost:8080/alerts/displayAlerts/1
GET http://localhost:8080/alerts/displayAlerts/2
GET http://localhost:8080/alerts/displayAlerts/3
GET http://localhost:8080/alerts/getAlertsCount/1
GET http://localhost:8080/alerts/getAlertsCount/2
GET http://localhost:8080/alerts/getAlertsCount/3

POST http://localhost:8080/retweets/create?userId=1&postId=1
GET http://localhost:8080/retweets/user/2
GET http://localhost:8080/retweets/post/2
GET http://localhost:8080/retweets/count/2
DELETE http://localhost:8080/retweets/undo?userId=1&postId=1
GET http://localhost:8080/retweets/check?userId=2&postId=2
