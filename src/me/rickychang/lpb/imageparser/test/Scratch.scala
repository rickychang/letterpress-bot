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
import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange
import net.sourceforge.javaocr.ocrPlugins.mseOCR.OCRScanner
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImageLoader
import net.sourceforge.javaocr.scanner.PixelImage
import java.util.HashMap
import java.util.ArrayList
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage
import java.awt.Image
import java.awt.Frame
import me.rickychang.lpb.imageparser.ParserUtil
import me.rickychang.lpb.imageparser.JavaOCRCharParser

@RunWith(classOf[JUnitRunner])
class ScratchSuite extends FunSuite {

  test("tile extraction util method test") {
    ParserUtil.extractTiles("images", "Photo Dec 09, 9 00 41 PM.png")
  }

  test("JavaOCR Char Parser") {
    val tileParser: JavaOCRCharParser = new JavaOCRCharParser("images/training/light")
    val img: BufferedImage = ImageIO.read(new File("images/test/iphone5-twitter-board2.jpg"))
    val imageParser = new IPhone5BoardParser(img, tileParser)
    println(imageParser.toString)
  }

}