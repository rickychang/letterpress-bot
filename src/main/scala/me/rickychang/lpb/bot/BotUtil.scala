package me.rickychang.lpb.bot

object BotUtil {
  def truncateTweet(tweetText: String) = {
    if (tweetText.length <= 140) tweetText
    else tweetText.substring(0, 140) + "\u2026"
  }
}