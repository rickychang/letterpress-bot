package me.rickychang.lpb.scratch

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.typesafe.config.ConfigFactory
import me.rickychang.lpb.solver.BoardSolver
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.TwitterStream
import twitter4j.TwitterStreamFactory
import twitter4j.UserStreamListener
import twitter4j.auth.AccessToken
import me.rickychang.lpb.solver.WordDictionary
import twitter4j.StallWarning
import twitter4j.UserList
import twitter4j.DirectMessage
import twitter4j.StatusDeletionNotice
import twitter4j.User
import twitter4j.Status
import twitter4j.StatusUpdate
import me.rickychang.lpb.imageparser.IPhone5BoardParser
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import me.rickychang.lpb.imageparser.JavaOCRCharParser
import me.rickychang.lpb.imageparser.ColorHistogramTileStateParser
import java.net.URL

@RunWith(classOf[JUnitRunner])
class ScratchSuite extends FunSuite {
  
  test("Scratch") {
  
  }

  test("Config test") {
    val conf = ConfigFactory.load
    println(conf.getString("consumer.key"))
  }

}
