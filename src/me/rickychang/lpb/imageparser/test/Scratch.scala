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
import java.awt.image.DataBufferInt
import java.awt.Color
import java.awt.Rectangle
import me.rickychang.lpb.imageparser.ColorHistogram

@RunWith(classOf[JUnitRunner])
class ScratchSuite extends FunSuite {

  test("JavaOCR Char Parser") {
    val tileParser: JavaOCRCharParser = new JavaOCRCharParser("images/training/light")
    val img: BufferedImage = ImageIO.read(new File("images/test/iphone5-twitter-board2.jpg"))
    val imageParser = new IPhone5BoardParser(img, tileParser)
    println(imageParser.toString)
  }
  
  test("Color histogram scratch") {
    val tileParser: JavaOCRCharParser = new JavaOCRCharParser("images/training/light")
    val img: BufferedImage = ImageIO.read(new File("images/test/iphone5-twitter-board2.jpg"))
    val imageParser = new IPhone5BoardParser(img, tileParser)
    for (tile <- imageParser.tileImages) {
      val imgData = tile.getData(new Rectangle(116, 116)).getDataBuffer().asInstanceOf[DataBufferInt].getData()
      val pixels = imgData map { new Color(_) }
      val colorHistogram = pixels.groupBy(identity).mapValues(_.size).toList.sortWith { (e1, e2) => (e1._2 > e2._2) }
      val domColors = colorHistogram.take(1)
      println(domColors)
    }
  }
  
  test("ColorHistogram") {
    val tileParser: JavaOCRCharParser = new JavaOCRCharParser("images/training/light")
    val img: BufferedImage = ImageIO.read(new File("images/test/iphone5-test-board1.png"))
    val imageParser = new IPhone5BoardParser(img, tileParser)
      for (tile <- imageParser.tileImages) {
      val colorHistogram = new ColorHistogram(tile)
      println(colorHistogram.dominantColor)
    }
  }

}