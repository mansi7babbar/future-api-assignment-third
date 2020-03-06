package com.knoldus

import com.typesafe.config.{Config, ConfigFactory}
import twitter4j.auth.AccessToken
import twitter4j.{Twitter, TwitterFactory}

class TwitterSetup {
  def getTwitterInstance: Twitter = {
    val config: Config = ConfigFactory.load()
    val twitter: Twitter = new TwitterFactory().getInstance()
    twitter.setOAuthConsumer(config.getString("consumer.key"), config.getString("consumer.secret"))
    twitter.setOAuthAccessToken(new AccessToken(config.getString("token.key"), config.getString("token.secret")))
    twitter
  }
}
