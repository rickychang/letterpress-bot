package me.rickychang.lpb.bot

import com.typesafe.config.ConfigFactory
import twitter4j.TwitterFactory
import twitter4j.TwitterStreamFactory
import twitter4j.auth.AccessToken
import me.rickychang.lpb.solver.BoardSolver
import me.rickychang.lpb.solver.WordDictionary

object SimpleSolverBot extends App {
  val conf = ConfigFactory.load

  val botUserId = conf.getString("bot.userid").toLong
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

  val boardSolver = new BoardSolver(new WordDictionary)

  twitterStream.addListener(new SimpleStatusListener(botUserId, twitterRestClient, boardSolver))
  twitterStream.user()
  
}