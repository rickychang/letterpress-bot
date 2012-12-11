package me.rickychang.lpb.imageparser.test

import javax.imageio.ImageIO
import java.io.File
import me.rickychang.lpb.imageparser.IPhone5BoardParser
import me.rickychang.lpb.imageparser.JavaOCRCharParser
import me.rickychang.lpb.imageparser.ColorHistogramTileStateParser

object IPhone5ImageParserApp extends App {

  override def main(args: Array[String]) {
	  if (args.size < 1) {
	    println("You must provide a file name")
	  } else {
	    val img = ImageIO.read(new File(args(0)))
	    val tileParser: JavaOCRCharParser = new JavaOCRCharParser("images/training/light")
	    val imgParser = new IPhone5BoardParser(img, tileParser, ColorHistogramTileStateParser)
	    println(imgParser.toString)
	  }
  }

}