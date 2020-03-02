package com.knoldus

import twitter4j.auth.AccessToken
import twitter4j.{Twitter, TwitterFactory}

class TwitterSetup {

  val consumerKey = "e6uS4phTxImI68qlA6h4V3zwR"
  val consumerSecret = "M8b4Q3sudgU9mNZgJx1onUlqQYi5h5YCK1GVacjAc8yHDAohFc"
  val token = "160922224-AKOoOasbqi3huqT7uyq4Og0Oqlucn8rKeD9IcUvU"
  val tokenSecret = "7HgIJUmjOX2AZThvVp7RPWsZwOrW1ffpvkEpjeBSQynnH"

  val twitter: Twitter = new TwitterFactory().getInstance()
  twitter.setOAuthConsumer("e6uS4phTxImI68qlA6h4V3zwR",
    "M8b4Q3sudgU9mNZgJx1onUlqQYi5h5YCK1GVacjAc8yHDAohFc")
  twitter.setOAuthAccessToken(new AccessToken(
    "160922224-AKOoOasbqi3huqT7uyq4Og0Oqlucn8rKeD9IcUvU",
    "7HgIJUmjOX2AZThvVp7RPWsZwOrW1ffpvkEpjeBSQynnH"))

}
