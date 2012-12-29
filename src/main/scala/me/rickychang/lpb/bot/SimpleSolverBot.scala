package me.rickychang.lpb.bot

import com.typesafe.config.ConfigFactory

import twitter4j.TwitterFactory
import twitter4j.TwitterStreamFactory
import twitter4j.auth.AccessToken

object SimpleSolverBot extends App {
  val conf = ConfigFactory.load
  val botUserId = conf.getLong("bot.userid")
  val consumerKey = conf.getString("consumer.key")
  val consumerSecret = conf.getString("consumer.secret")
  val accessToken = conf.getString("access.token")
  val accessSecret = conf.getString("access.secret")
  val oAuthAccessToken = new AccessToken(accessToken, accessSecret)
  
  val twitterStream = new TwitterStreamFactory().getInstance()
  val twitterRestClient = new TwitterFactory().getInstance()

  twitterStream.setOAuthConsumer(consumerKey, consumerSecret)
  twitterRestClient.setOAuthConsumer(consumerKey, consumerSecret)
  twitterStream.setOAuthAccessToken(oAuthAccessToken)
  twitterRestClient.setOAuthAccessToken(oAuthAccessToken)
  twitterStream.addListener(new SimpleStatusListener(botUserId, twitterRestClient))
  twitterStream.user()
  
}