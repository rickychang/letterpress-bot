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
import me.rickychang.lpb.imageparser.JavaOCRCharParser

@RunWith(classOf[JUnitRunner])
class IPhone5ParsingSuite extends FunSuite {

  val tileParser: JavaOCRCharParser = new JavaOCRCharParser("images/training/light")
  
  test("iPhone 5 screenshot tile extraction") {
    try {
      val img: BufferedImage = ImageIO.read(new File("images/test/iphone5-test-board1.png"))
      val imageParser = new IPhone5BoardParser(img, tileParser)
      var i = 0
      for (img <- imageParser.tileImages) {
        i = i+1
        ImageIO.write(img, "png", new File("out/tile_%d.png".format(i)))
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
  
  test("twitter board 1 parsing") {
    val img: BufferedImage = ImageIO.read(new File("images/test/iphone5-twitter-board1.jpg"))
    val imageParser = new IPhone5BoardParser(img, tileParser)
    val expectedBoardChars = List("FLHSO","YKAMW","FDRPV","LLXXR","ADGGS").mkString("\n")
    assert(imageParser.toString == expectedBoardChars)
  }
  
    test("twitter board 2 parsing") {
    val img: BufferedImage = ImageIO.read(new File("images/test/iphone5-twitter-board2.jpg"))
    val imageParser = new IPhone5BoardParser(img, tileParser)
    val expectedBoardChars = List("BNETS","SKPCN","SMTPH","SNJTV","FVPAA").mkString("\n")
    assert(imageParser.toString == expectedBoardChars)
  }

}