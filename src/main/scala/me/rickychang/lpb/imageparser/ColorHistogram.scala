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
    val intPixels = image.getData(new Rectangle(image.getWidth, image.getHeight)).getDataBuffer().asInstanceOf[DataBufferInt].getData()
    intPixels.groupBy(identity).mapValues(_.size)
  }

  val sortedHistogram = histogramMap.toList.sortWith { (e1, e2) => (e1._2 > e2._2) }
    .map { case (intPixel, count) => (new Color(intPixel), count) }

  val dominantColor = sortedHistogram.head._1

}
