package me.rickychang.lpb.bot

import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO
import me.rickychang.lpb.imageparser.ColorHistogramTileStateParser
import me.rickychang.lpb.imageparser.IPhone5BoardParser
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

class SimpleStatusListener(val myUserId: Long, val twitterRestClient: Twitter, val boardSolver: BoardSolver) extends UserStreamListener with Logging  {

  private val tweetLog = Logger("Tweets")

  def onStatus(status: Status) {
    try {
      val inReplyToUserId = status.getInReplyToUserId
      val attachedMedia = status.getMediaEntities
      val senderScreenName = status.getUser.getScreenName
      val statusId = status.getId
      // tweet is directed at bot
      if (inReplyToUserId == myUserId) {
        tweetLog.info("RECV\t%d\t%s\t%s\t%s".format(statusId, senderScreenName, status.getText, !attachedMedia.isEmpty))
        if (!attachedMedia.isEmpty) {
          val attachedImageURL = attachedMedia.head.getMediaURL
          val img = ImageIO.read(new URL(attachedImageURL))
          if (img != null) {
            // TODO: test invalid images
            val imageParser = new IPhone5BoardParser(img, new JavaOCRCharParser, ColorHistogramTileStateParser)
            logger.debug("Image: %s, parsed board: \n%s".format(attachedImageURL, imageParser.toString))
            val wordsToPlay = boardSolver.findWords(imageParser.gameBoard, 4).map {
              case (w, t) => val (p, o) = boardSolver.wordScore(t); "%s (+%d,%d)".format(w, p, o)
            }.mkString(", ")
            val tweetText = BotUtil.truncateTweet("@%s %s".format(senderScreenName, wordsToPlay))
            val statusUpdate = new StatusUpdate(tweetText)
            tweetLog.info("READY\t%d\t%s".format(statusId, statusUpdate.toString))
            statusUpdate.setInReplyToStatusId(statusId)
//             Temporarily removing sending of tweets.  We know this works, don't want to send tweets during dev/testing
//            val postedStatus = twitterRestClient.updateStatus(statusUpdate)
//            tweetLog.info("SENT\t%d\t%s\t%s".format(statusId, postedStatus.getInReplyToScreenName, postedStatus.getText))
          }
        }
      }
    } catch {
      case e: IIOException => logger.error("javax.imageio.IIOException: %s, %s".format(e.getMessage, e.getCause))
      case e: Exception => logger.error(e.toString + " " + e.getStackTrace().mkString("\n"))
    }
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
