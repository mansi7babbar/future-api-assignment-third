package com.knoldus

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AverageRetweetsPerTweet(retrieveTweets: RetrieveTweets) {
  def getAverageRetweetsPerTweet(hashTag: String): Future[Int] = {
    val tweets = retrieveTweets.retrieveTweet(hashTag: String)
    tweets.map { tweets =>
      val getRetweetCount = tweets.foldLeft(0) { (sum, tweet) =>
        sum + tweet.reTweetCount
      }
      getRetweetCount / tweets.length
    }
  }.recover({
    case ex: Exception => ex.getMessage
      0
  })
}
