package com.knoldus

import java.time.ZoneId

import twitter4j._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RetrieveTweets extends TwitterSetup {

  def searchHashtag(hashTag: String): List[Status] = {
    val hashTagQuery = new Query(hashTag)
    twitter.search(hashTagQuery).getTweets.asScala.toList
  }

  def retrieveTweet(hashTag: String): Future[List[CustomTweet]] = Future {
    searchHashtag(hashTag).map(retrievedTweet => CustomTweet(retrievedTweet.getId, retrievedTweet.getFavoriteCount,
      retrievedTweet.getRetweetCount, retrievedTweet.getCreatedAt
        .toInstant.atZone(ZoneId.systemDefault()).toLocalDate))
  }.recover({
    case ex: Exception => ex.getMessage
      List[CustomTweet]()
  })

}
