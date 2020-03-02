package com.knoldus

object TwitterDriver extends App {
  val retrievedTweets = TwitterOps.retrieveTweet("#sunday")
  val numberOfTweets = TwitterOps.getNumberOfTweets(retrievedTweets)
  val averageLikesPerTweet = TwitterOps.getAverageLikesPerTweet(retrievedTweets)
  val averageRetweetsPerTweet = TwitterOps.getAverageRetweetsPerTweet(retrievedTweets)
}
