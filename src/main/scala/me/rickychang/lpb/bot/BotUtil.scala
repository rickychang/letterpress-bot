package me.rickychang.lpb.bot

object BotUtil {

  def truncateTweet(tweetText: String) = {
    if (tweetText.length <= 140) tweetText
    // if tweet is too long, truncate and append ellipsis
    // unicode character
    else tweetText.substring(0, 140) + "\u2026"
  }

}