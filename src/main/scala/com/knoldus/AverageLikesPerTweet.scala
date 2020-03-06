package com.knoldus

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AverageLikesPerTweet {
  def getAverageLikesPerTweet(customTweets: Future[List[CustomTweet]]): Future[Int] = {
    customTweets.map { tweets =>
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
