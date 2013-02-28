package me.rickychang.lpb.imageparser

import java.awt.image.DataBufferInt
import java.awt.image.BufferedImage
import java.awt.Rectangle
import java.awt.Color

/**
 * Histogram of colors in an image.  Note: this implementation does not place the
 * raw colors into bins as you normally would.  Because the color space of
 * game tiles is very restricted, we simply compute the histogram of
 * all colors in the image.
 */
class ColorHistogram(image: BufferedImage) {

  val histogramMap = {
    val pixels = for {
      y <- 0 until image.getHeight
      x <- 0 until image.getWidth 
    } yield new Color(image.getRGB(x, y))
    pixels.groupBy(identity).mapValues(_.size)
  }
  
  private def getARGBPixelColor(pixel: Int): Color = {
    val a: Int = (pixel >> 24) & 0xff
    val r: Int = (pixel >> 16) & 0xff
    val g: Int = (pixel >> 8) & 0xff
    val b: Int = (pixel) & 0xff
    new Color(r, g, b, a)
  }

  val sortedHistogram = histogramMap.toList.sortWith { (e1, e2) => (e1._2 > e2._2) }

  val dominantColor = sortedHistogram.head._1

}
