package com.knoldus

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class NumberOfTweets(retrieveTweets: RetrieveTweets) {
  def getNumberOfTweets(hashTag: String): Future[Int] = {
    val tweets = retrieveTweets.retrieveTweet(hashTag: String)
    tweets.map { tweets =>
      tweets.length
    }
  }.recover({
    case ex: Exception => ex.getMessage
      0
  })
}
