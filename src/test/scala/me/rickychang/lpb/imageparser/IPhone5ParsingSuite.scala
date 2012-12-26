package me.rickychang.lpb.imageparser

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import javax.imageio.ImageIO
import me.rickychang.lpb.board.TileColors

@RunWith(classOf[JUnitRunner])
class IPhone5ParsingSuite extends FunSuite {

  val tileParser: JavaOCRCharParser = new JavaOCRCharParser("/images/training/light")
  
  test("iPhone 5 screenshot tile extraction") {
    try {
      val img: BufferedImage = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board1.png"))
      val imageParser = new IPhone5BoardParser(img, tileParser, ColorHistogramTileStateParser)
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
    val img: BufferedImage = ImageIO.read(getClass.getResource("/images/test/iphone5-twitter-board1.jpg"))
    val imageParser = new IPhone5BoardParser(img, tileParser, ColorHistogramTileStateParser)
    val expectedBoardChars = List("FR LR HR Sr Ob",
    							  "Yr Kr Ar Mb Wr",
    							  "Fw Dw Rr Pb Vw",
    							  "Lb Lr Xr Xw Rb",
    							  "Ar Dw Gr Gw Sr").mkString("\n")
    assert(imageParser.toString == expectedBoardChars)
  }
  
    test("twitter board 2 parsing") {
    val img: BufferedImage = ImageIO.read(getClass.getResource("/images/test/iphone5-twitter-board2.jpg"))
    val imageParser = new IPhone5BoardParser(img, tileParser, ColorHistogramTileStateParser)
    val expectedBoardChars = List("Br Nb Eb Tr SR",
    							  "Sb Kb Pr Cb Nr",
    							  "SB Mb Tw Pw Hr",
    							  "Sb Nw Jw Tr Vw",
    							  "Fr Vw Pw Ab Ab").mkString("\n")
    println(imageParser.toString)
    assert(imageParser.toString == expectedBoardChars)
  }
    
  test("Color diff") {
    val c1 = new Color(247,153, 141)
    val c2 = new Color(247, 153, 141)
    val c3 = new Color(119, 200, 245)
    val c4 = new Color(120, 200, 245)
    assert(TileColors.colorDiff(c1, c2).toInt == 0)
    assert(TileColors.colorDiff(c1, c3).toInt == 171)
    assert(TileColors.colorDiff(c3, c4).toInt == 1)
    
  }

}