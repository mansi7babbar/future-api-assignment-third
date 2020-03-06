package com.knoldus

import java.time.Duration

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AverageTweetsPerDay {
  def getAverageTweetsPerDay(customTweets: Future[List[CustomTweet]]): Future[Long] = {
    val sortedPosts = customTweets.map(post => post.sortWith((a, b) => a.createdAt.isBefore(b.createdAt)))
    val start = sortedPosts.map(sortedPost => sortedPost.head.createdAt)
    val end = sortedPosts.map(sortedPost => sortedPost.reverse.head.createdAt)
    val localStart = start.map(start => start.atStartOfDay())
    val localEnd = end.map(end => end.atStartOfDay())
    val duration = localEnd.map(localEnd =>
      localStart.map(localStart =>
        Duration.between(localStart, localEnd))).flatten
    duration.map(duration => customTweets.map(posts => posts.length / (duration.getSeconds / 86400))).flatten
  }.recover({
    case ex: Exception => ex.getMessage
      0
  })
}
