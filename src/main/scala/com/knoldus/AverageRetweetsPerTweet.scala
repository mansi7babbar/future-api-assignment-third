package com.knoldus

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AverageRetweetsPerTweet {
  def getAverageRetweetsPerTweet(customTweets: Future[List[CustomTweet]]): Future[Int] = {
    customTweets.map { tweets =>
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
