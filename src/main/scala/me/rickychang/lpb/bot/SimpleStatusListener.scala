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

class SimpleStatusListener(val twitterUserId: Long, val twitterRestClient: Twitter) extends UserStreamListener  {
  val boardSolver = new BoardSolver(new WordDictionary)
  private val conf = ConfigFactory.load
  private val tweetLog = Logger("Tweets")

  def onStatus(status: Status) {
    val inReplyToUserId = status.getInReplyToUserId
    val attachedMedia = status.getMediaEntities
    val senderScreenName = status.getUser.getScreenName
    val statusId = status.getId
    if (inReplyToUserId == twitterUserId) {
      tweetLog.info("RECV\t%d\t%s\t%s\t%s".format(statusId, senderScreenName, status.getText, !attachedMedia.isEmpty))
      // tweet is directed at bot
      if (!attachedMedia.isEmpty) {
        // TODO: test bad image URLs
        val img = ImageIO.read(new URL(attachedMedia.head.getMediaURL))
        if (img != null) {
          // TODO: test invalid images
          val imageParser = new IPhone5BoardParser(img, new JavaOCRCharParser, ColorHistogramTileStateParser)
          val wordsToPlay = boardSolver.findWords(imageParser.gameBoard, 3).map {
            case (w, t) => val (p, o) = boardSolver.wordScore(t); "%s (+%d,%d)".format(w, p, o)
          }.mkString(", ")
          // TODO: test long tweets
          val statusUpdate = new StatusUpdate("@%s %s".format(senderScreenName, wordsToPlay))
          statusUpdate.setInReplyToStatusId(statusId)
          val postedStatus = twitterRestClient.updateStatus(statusUpdate)
          tweetLog.info("SENT\t%d\t%s\t%s".format(statusId, postedStatus.getInReplyToScreenName, postedStatus.getText))
        }
      }
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
