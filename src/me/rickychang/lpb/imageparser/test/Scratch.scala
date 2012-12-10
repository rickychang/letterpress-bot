package me.rickychang.lpb.imageparser.test

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import scala.collection.mutable.ListBuffer
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import javax.imageio.ImageIO
import me.rickychang.lpb.imageparser.ParserUtil.getResizedDimension
import me.rickychang.lpb.imageparser.ParserUtil.getTileWidthHeight
import me.rickychang.lpb.imageparser.ParserUtil.resizeImage
import me.rickychang.lpb.imageparser.IPhone5BoardParser

@RunWith(classOf[JUnitRunner])
class ScratchSuite extends FunSuite {

  test("Twitter iPhone 5 screenshot tile extraction") {
    try {
      val img: BufferedImage = ImageIO.read(new File("test/iphone5-twitter-board1.jpg"))
      val imageParser = new IPhone5BoardParser(img)
      var i = 0
      for (img <- imageParser.tileImages) {
        i = i+1
        ImageIO.write(img, "png", new File("out/tile_%d.png".format(i)))
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

}