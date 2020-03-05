package com.knoldus

import java.sql.Timestamp
import java.time.Duration

import twitter4j._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TwitterOps extends TwitterSetup {

  def retrieveTweet(hashTag: String): Future[List[Status]] = Future {
    val hashTagQuery = new Query(hashTag)
    val retrievedTweets = twitter.search(hashTagQuery).getTweets.asScala.toList
    retrievedTweets
  }

  def getNumberOfTweets(tweets: Future[List[Status]]): Future[Int] = {
    tweets.map { tweets =>
      tweets.length
    }
  }

  def getAverageLikesPerTweet(tweets: Future[List[Status]]): Future[Int] = {
    tweets.map { tweets =>
      val getFavouriteCount = tweets.foldLeft(0) { (sum, tweet) =>
        sum + tweet.getFavoriteCount
      }
      getFavouriteCount / tweets.length
    }
  }

  def getAverageRetweetsPerTweet(tweets: Future[List[Status]]): Future[Int] = {
    tweets.map { tweets =>
      val getRetweetCount = tweets.foldLeft(0) { (sum, tweet) =>
        sum + tweet.getRetweetCount
      }
      getRetweetCount / tweets.length
    }
  }

  def getAverageTweetsPerDay(posts: Future[List[Status]]): Future[Long] = {
    val sortedPosts = posts.map(post => post.sortWith((a, b) => a.getCreatedAt.before(b.getCreatedAt)))
    val start = sortedPosts.map(sortedPost => sortedPost.head.getCreatedAt)
    val end = sortedPosts.map(sortedPost => sortedPost.reverse.head.getCreatedAt)
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
