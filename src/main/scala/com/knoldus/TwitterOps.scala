package com.knoldus

import java.sql.Timestamp
import java.time.Duration
import java.util.Date

import twitter4j._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class CustomTweet(id: Long, favoriteCount: Int, reTweetCount: Int, createdAt: Date)

object TwitterOps extends TwitterSetup {

  def retrieveTweet(hashTag: String): Future[List[CustomTweet]] = Future {
    val hashTagQuery = new Query(hashTag)
    val retrievedTweets = twitter.search(hashTagQuery).getTweets.asScala.toList
    retrievedTweets.map(retrievedTweet => CustomTweet(retrievedTweet.getId, retrievedTweet.getFavoriteCount,
      retrievedTweet.getRetweetCount, retrievedTweet.getCreatedAt))
  }.fallbackTo(Future {
    -1
  })

  def getNumberOfTweets(tweets: Future[List[CustomTweet]]): Future[Int] = {
    tweets.map { tweets =>
      tweets.length
    }
  }.fallbackTo(Future {
    -1
  })

  def getAverageLikesPerTweet(tweets: Future[List[CustomTweet]]): Future[Int] = {
    tweets.map { tweets =>
      val getFavouriteCount = tweets.foldLeft(0) { (sum, tweet) =>
        sum + tweet.favoriteCount
      }
      getFavouriteCount / tweets.length
    }
  }.fallbackTo(Future {
    -1
  })

  def getAverageRetweetsPerTweet(tweets: Future[List[CustomTweet]]): Future[Int] = {
    tweets.map { tweets =>
      val getRetweetCount = tweets.foldLeft(0) { (sum, tweet) =>
        sum + tweet.reTweetCount
      }
      getRetweetCount / tweets.length
    }
  }.fallbackTo(Future {
    -1
  })

  def getAverageTweetsPerDay(posts: Future[List[CustomTweet]]): Future[Long] = {
    val sortedPosts = posts.map(post => post.sortWith((a, b) => a.createdAt.before(b.createdAt)))
    val start = sortedPosts.map(sortedPost => sortedPost.head.createdAt)
    val end = sortedPosts.map(sortedPost => sortedPost.reverse.head.createdAt)
    val localStart = start.map(start => new Timestamp(start.getTime).toLocalDateTime)
    val localEnd = end.map(end => new Timestamp(end.getTime).toLocalDateTime)
    val duration = localEnd.map(localEnd =>
      localStart.map(localStart =>
        Duration.between(localStart, localEnd))).flatten
    duration.map(duration => posts.map(posts => posts.length / (duration.getSeconds / 86400))).flatten
  }.fallbackTo(Future {
    -1
  })

}
