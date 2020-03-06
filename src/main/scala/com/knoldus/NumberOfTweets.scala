package com.knoldus

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class NumberOfTweets {
  def getNumberOfTweets(customTweets: Future[List[CustomTweet]]): Future[Int] = {
    customTweets.map { tweets =>
      tweets.length
    }
  }.recover({
    case ex: Exception => ex.getMessage
      0
  })
}
