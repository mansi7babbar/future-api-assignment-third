package com.knoldus

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
      val getFavouriteCount = tweets.foldLeft(0) { (_, tweet) =>
        tweet.getFavoriteCount
      }
      getFavouriteCount / tweets.length
    }
  }

  def getAverageRetweetsPerTweet(tweets: Future[List[Status]]): Future[Int] = {
    tweets.map { tweets =>
      val getRetweetCount = tweets.foldLeft(0) { (_, tweet) =>
        tweet.getRetweetCount
      }
      getRetweetCount / tweets.length
    }
  }
}
