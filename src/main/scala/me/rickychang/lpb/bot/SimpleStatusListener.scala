package me.rickychang.lpb.bot

import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO
import me.rickychang.lpb.imageparser.TileStateParser
import me.rickychang.lpb.imageparser.JavaOCRCharParser
import me.rickychang.lpb.solver.BoardSolver
import me.rickychang.lpb.solver.WordDictionary
import twitter4j.DirectMessage
import twitter4j.StallWarning
import twitter4j.Status
import twitter4j.StatusDeletionNotice
import twitter4j.StatusUpdate
import twitter4j.Twitter
import twitter4j.User
import twitter4j.UserList
import twitter4j.UserStreamListener
import com.typesafe.config.ConfigFactory
import com.weiglewilczek.slf4s.Logger
import com.weiglewilczek.slf4s.Logging
import javax.imageio.IIOException
import me.rickychang.lpb.imageparser.IPhone5Parser
import me.rickychang.lpb.imageparser.MultiDeviceParser
import me.rickychang.lpb.imageparser.InvalidImageException
import me.rickychang.lpb.board.InvalidTilesException
import me.rickychang.lpb.replay.ReplayUtils

class SimpleStatusListener(myUserId: Long, twitterRestClient: Twitter, boardSolver: BoardSolver) extends UserStreamListener with Logging {

  val NumWordsToReturn = 3
  private val tLog = Logger("Tweets")
  private val bParser = new MultiDeviceParser(new JavaOCRCharParser)

  def onStatus(status: Status) {
    try {
      if (status.getInReplyToUserId == myUserId) {
        _logReceivedTweet(status)
        val attachedMedia = status.getMediaEntities
        if (!attachedMedia.isEmpty) {
          logger.debug("Fetching image: %s".format(attachedMedia.head.getMediaURL))
          val img = ImageIO.read(new URL(attachedMedia.head.getMediaURL))
          val playedWords = status.getURLEntities.map(_.getDisplayURL).map{
            (url: String) =>
              val javaUrl = new URL(url)
            if (javaUrl.getHost == "replay.letterpress.atebits.com" &&
                javaUrl.getRef.length == 32)
              ReplayUtils.fetchJson("http://replay.letterpress.atebits.com" +
                                    javaUrl.getRef)
            else
              ""
          }.map(ReplayUtils.playedWords(_)).reduceLeft(_ | _)
          val tweetText = getResponseTweetText(img, playedWords, status.getUser.getScreenName)
          if (tweetText.isDefined) {
            val responseUpdate = new StatusUpdate(tweetText.get)
            responseUpdate.setInReplyToStatusId(status.getId)
            _logReadyTweet(responseUpdate)
            val sentStatus = twitterRestClient.updateStatus(responseUpdate)
            _logSentTweet(sentStatus)
          } else {
            throw new InvalidImageException("Unable to find playble words for corresponding board: %s".format(status.getText))
          }
        }
      }
    } catch {
      case e @ (_: InvalidImageException | _: InvalidImageException) => logger.error(e.getMessage)
      case e: IIOException => logger.error("javax.imageio.IIOException: %s, %s".format(e.getMessage, e.getCause))
      case e: Exception => logger.error(e.toString + " " + e.getStackTrace().mkString("\n"))
    }
  }

  private def getResponseTweetText(screenshot: BufferedImage, playedWords: Set[String], sender: String): Option[String] = {
    val b = bParser.parseGameBoard(screenshot)
    boardSolver.findWords(b, playedWords, NumWordsToReturn) match {
      case Some(words) => Some(BotUtil.truncateTweet("@%s %s".format(sender, words)))
      case _ => None
    }

  }

  private def _logReceivedTweet(s: Status): Unit = {
    tLog.info("RECV\t%d\t%s\t%s\t%s".format(s.getId,
      s.getUser.getScreenName,
      s.getText,
      !s.getMediaEntities.isEmpty))
  }

  private def _logReadyTweet(s: StatusUpdate): Unit = {
    tLog.info("READY\t%d\t%s".format(s.getInReplyToStatusId, s.getStatus))
  }

  private def _logSentTweet(s: Status): Unit = {
    tLog.info("SENT\t%d\t%s\t%s".format(s.getInReplyToStatusId, s.getInReplyToScreenName, s.getText))
  }

  // empty methods required by UserStreamListener 
  def onBlock(source: User, blockedUser: User) = {}
  def onDeletionNotice(directMessageId: Long, userId: Long) = {}
  def onDirectMessage(directMessage: DirectMessage) = {}
  def onFavorite(source: User, target: User, favoritedStatus: Status) = {}
  def onFollow(source: User, followedUser: User) = {}
  def onFriendList(friendIds: Array[Long]) = {}
  def onUnblock(source: User, unblockedUser: User) = {}
  def onUnfavorite(source: User, target: User, unfavoritedStatus: Status) = {}
  def onUserListCreation(listOwner: User, list: UserList) = {}
  def onUserListDeletion(listOwner: User, list: UserList) = {}
  def onUserListMemberAddition(addedMember: User, listOwner: User, list: UserList) = {}
  def onUserListMemberDeletion(deletedMember: User, listOwner: User, list: UserList) = {}
  def onUserListSubscription(subscriber: User, listOwner: User, list: UserList) = {}
  def onUserListUnsubscription(subscriber: User, listOwner: User, list: UserList) = {}
  def onUserListUpdate(listOwner: User, list: UserList) = {}
  def onUserProfileUpdate(updatedUser: User) = {}
  def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) = {}
  def onTrackLimitationNotice(numberOfLimitedStatuses: Int) = {}
  def onScrubGeo(userId: Long, upToStatusId: Long) = {}
  def onStallWarning(warning: StallWarning) = {}
  def onException(ex: Exception) = {}
}
