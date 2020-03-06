package com.knoldus

import java.time.ZoneId

import twitter4j._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RetrieveTweets(twitterSetup: TwitterSetup) {

  def retrieveTweet(hashTag: String): Future[List[CustomTweet]] = Future {
    val hashTagQuery = new Query(hashTag)
    val tweetStatus = twitterSetup.getTwitterInstance.search(hashTagQuery).getTweets.asScala.toList
    tweetStatus.map(retrievedTweet => CustomTweet(retrievedTweet.getId, retrievedTweet.getFavoriteCount,
      retrievedTweet.getRetweetCount, retrievedTweet.getCreatedAt
        .toInstant.atZone(ZoneId.systemDefault()).toLocalDate))
  }.recover({
    case ex: Exception => ex.getMessage
      List[CustomTweet]()
  })

}
