package com.knoldus

import java.sql.Timestamp
import java.time.Duration
import java.util.Date

import twitter4j._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class CustomTweet(id: Long, favoriteCount: Int, reTweetCount: Int, createdAt: Date)

class TwitterOps extends TwitterSetup {

  def retrieveTweet(hashTag: String): Future[List[CustomTweet]] = Future {
    val hashTagQuery = new Query(hashTag)
    val retrievedTweets = twitter.search(hashTagQuery).getTweets.asScala.toList
    retrievedTweets.map(retrievedTweet => CustomTweet(retrievedTweet.getId, retrievedTweet.getFavoriteCount,
      retrievedTweet.getRetweetCount, retrievedTweet.getCreatedAt))
  }

  def getNumberOfTweets(hashTag: String): Future[Int] = {
    val tweets = retrieveTweet(hashTag: String)
    tweets.map { tweets =>
      tweets.length
    }
  }

  def getAverageLikesPerTweet(hashTag: String): Future[Int] = {
    val tweets = retrieveTweet(hashTag: String)
    tweets.map { tweets =>
      val getFavouriteCount = tweets.foldLeft(0) { (sum, tweet) =>
        sum + tweet.favoriteCount
      }
      getFavouriteCount / tweets.length
    }
  }

  def getAverageRetweetsPerTweet(hashTag: String): Future[Int] = {
    val tweets = retrieveTweet(hashTag: String)
    tweets.map { tweets =>
      val getRetweetCount = tweets.foldLeft(0) { (sum, tweet) =>
        sum + tweet.reTweetCount
      }
      getRetweetCount / tweets.length
    }
  }

  def getAverageTweetsPerDay(hashTag: String): Future[Long] = {
    val posts = retrieveTweet(hashTag: String)
    val sortedPosts = posts.map(post => post.sortWith((a, b) => a.createdAt.before(b.createdAt)))
    val start = sortedPosts.map(sortedPost => sortedPost.head.createdAt)
    val end = sortedPosts.map(sortedPost => sortedPost.reverse.head.createdAt)
    val localStart = start.map(start => new Timestamp(start.getTime).toLocalDateTime)
    val localEnd = end.map(end => new Timestamp(end.getTime).toLocalDateTime)
    val duration = localEnd.map(localEnd =>
      localStart.map(localStart =>
        Duration.between(localStart, localEnd))).flatten
    duration.map(duration => posts.map(posts => posts.length / (duration.getSeconds / 86400))).flatten
  }

}
