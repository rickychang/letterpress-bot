package me.rickychang.lpb.imageparser

import java.awt.image.DataBufferInt
import java.awt.image.BufferedImage
import java.awt.Rectangle
import java.awt.Color

/**
 * Histogram of colors in image.  Note: this implementation does not place the
 * raw colors into bins as you normally would.  Because the color space of
 * game tiles is very restricted, we simply compute the histogram of
 * all colors in the image.
 */
class ColorHistogram(image: BufferedImage) {

  private val intPixels = image.getData(new Rectangle(image.getWidth, image.getHeight)).getDataBuffer().asInstanceOf[DataBufferInt].getData()

  private val sortedColorHistogram = {
    intPixels.groupBy(identity).mapValues(_.size).toList.sortWith { (e1, e2) => (e1._2 > e2._2) }
      .map { case (intPixel, count) => (new Color(intPixel), count) }
  }

  val dominantColor = sortedColorHistogram.head._1

  lazy val histogram = sortedColorHistogram.toMap

}
