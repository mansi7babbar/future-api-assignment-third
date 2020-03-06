package com.knoldus

import java.time.LocalDate

import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class AverageLikesPerTweetSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar {
  val customTweets: Future[List[CustomTweet]] = Future.successful(List(
    CustomTweet(1235800043564576768L, 0, 2, LocalDate.of(2020, 3, 6)),
    CustomTweet(1235800022345756672L, 0, 5, LocalDate.of(2020, 3, 6)),
    CustomTweet(1235800000925327362L, 0, 1, LocalDate.of(2020, 3, 6))))

  val averageLikesPerTweet = new AverageLikesPerTweet
  "getAverageLikesPerTweet" should "give average number of likes per tweet" in {
    averageLikesPerTweet.getAverageLikesPerTweet(customTweets).map(averageLikesPerTweet => assert(averageLikesPerTweet == 0))
  }
}
