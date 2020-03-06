package com.knoldus

import java.time.LocalDate

import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class NumberOfTweetsSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar {
  val mockRetrieveTweets: RetrieveTweets = mock[RetrieveTweets]

  when(mockRetrieveTweets.retrieveTweet("#sunday"))
    .thenReturn(Future.successful(List(CustomTweet(1235800043564576768L, 0, 2, LocalDate.of(2020, 3, 6)),
      CustomTweet(1235800022345756672L, 0, 5, LocalDate.of(2020, 3, 6)),
      CustomTweet(1235800000925327362L, 0, 1, LocalDate.of(2020, 3, 6)))))

  val numberOfTweets = new NumberOfTweets(mockRetrieveTweets)
  "getNumberOfTweets" should "give number of tweets" in {
    numberOfTweets.getNumberOfTweets("#sunday").map(numberOfTweets => assert(numberOfTweets == 3))
  }
}
