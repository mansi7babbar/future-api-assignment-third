package com.knoldus

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AverageLikesPerTweet(retrieveTweets: RetrieveTweets) {
  def getAverageLikesPerTweet(hashTag: String): Future[Int] = {
    val tweets = retrieveTweets.retrieveTweet(hashTag: String)
    tweets.map { tweets =>
      val getFavouriteCount = tweets.foldLeft(0) { (sum, tweet) =>
        sum + tweet.favoriteCount
      }
      getFavouriteCount / tweets.length
    }
  }.recover({
    case ex: Exception => ex.getMessage
      0
  })
}
