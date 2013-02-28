package me.rickychang.lpb.imageparser

import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import scala.math.pow
import scala.math.sqrt
import javax.imageio.ImageIO
import java.awt.image.ColorConvertOp
import java.awt.color.ColorSpace
import java.awt.image.LookupOp
import java.awt.image.ShortLookupTable
import ij.ImagePlus
import ij.IJ

object ParserUtil {

  val TilesPerRowColumn = 5

  val TilesPerBoard = 25

  val DefaultTrainingImagePath = "/images/training"

  val Black = new Color(0, 0, 0)

  val White = new Color(255, 255, 255)


  val CropThreshold = 7

  val colorInvertTable = {
    val lookupArray = new Array[Short](256)
    for (i <- lookupArray.indices) lookupArray(i) = (255 - i).toShort
    lookupArray
  }

  def getResizedDimension(origDim: Int): Int = {
    (origDim + TilesPerRowColumn - 1) / TilesPerRowColumn * TilesPerRowColumn
  }

  def getTileWidthHeight(boardWidthHeight: Int) = {
    boardWidthHeight / TilesPerRowColumn
  }

  /**
   * Method for resizing images.  Used to make the math of extracting individual tiles
   * easier.
   */
  def resizeImage(originalImage: BufferedImage, width: Int, height: Int): BufferedImage = {
    val scaledImage: BufferedImage = new BufferedImage(width, height, originalImage.getType)
    val graphics2D: Graphics2D = scaledImage.createGraphics()
    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    graphics2D.drawImage(originalImage, 0, 0, width, height, null)
    graphics2D.dispose()
    scaledImage
  }

  /**
   * Compute Euclidean distance between to Color objects
   * using RGB values
   */
  def colorDiff(c1: Color, c2: Color): Double = {
    val rDiff = c1.getRed - c2.getRed
    val gDiff = c1.getGreen - c2.getGreen
    val bDiff = c1.getBlue - c2.getBlue
    sqrt(pow(rDiff, 2) + pow(gDiff, 2) + pow(bDiff, 2))
  }

  /**
   * Normalize color by returning most similar canonical color.
   */
  def normalizeColor(canonicalColors: Iterable[Color], c: Color): Color = {
    canonicalColors.map(tileColor => (tileColor, colorDiff(c, tileColor))).minBy(_._2)._1
  }

  def convertToBinaryImage(orig: BufferedImage): BufferedImage = {
    val imgPlus = new ImagePlus("", orig)
    IJ.run(imgPlus, "Convert to Mask", "")
    imgPlus.getBufferedImage
  }

  def invertColors(orig: BufferedImage): BufferedImage = {
    val inverted = new BufferedImage(orig.getWidth, orig.getHeight, orig.getType)
    new LookupOp(new ShortLookupTable(0, colorInvertTable), null).filter(orig, inverted)
    inverted
  }

  def getBinaryCroppedTile(tileImage: BufferedImage): BufferedImage = {
    var bTile = convertToBinaryImage(tileImage)
    val domColor = new ColorHistogram(bTile).dominantColor
    if (domColor == Black) invertColors(bTile) else bTile
    //    cropLetter(bTile)
  }

  def cropLetter(binaryTile: BufferedImage): BufferedImage = {
    var topPadding, bottomPadding, leftPadding, rightPadding = -1
    val raster = binaryTile.getData()
    for (row <- 0 until binaryTile.getHeight) {
      val rowArray: Array[Int] = raster.getPixels(0, row, binaryTile.getWidth, 1, null)
      val histogram = rowArray.groupBy(identity).mapValues(_.size).toList.sortWith { (e1, e2) => (e1._2 > e2._2) }
      if (topPadding == -1 && histogram.length >= 2 && histogram(1)._2 > CropThreshold) {
        topPadding = row
      }
      if (bottomPadding == -1 && topPadding != -1 &&
        (histogram.length == 1 || histogram(1)._2 <= CropThreshold)) {
        bottomPadding = row
      }
    }
    for (col <- 0 until binaryTile.getWidth) {
      val colArray: Array[Int] = raster.getPixels(col, 0, 1, binaryTile.getHeight, null)
      val histogram = colArray.groupBy(identity).mapValues(_.size).toList.sortWith { (e1, e2) => (e1._2 > e2._2) }
      if (leftPadding == -1 && histogram.length >= 2 && histogram(1)._2 > CropThreshold) {
        leftPadding = col
      }
      if (rightPadding == -1 && leftPadding != -1 &&
        (histogram.length == 1 || histogram(1)._2 <= CropThreshold)) {
        rightPadding = col
      }
    }
    binaryTile.getSubimage(leftPadding, topPadding, rightPadding - leftPadding, bottomPadding - topPadding)
  }

  def cosineSimilarity(x: Vector[Float], y: Vector[Float]): Float = {
    require(x.size == y.size)
    dotProduct(x, y) / (magnitude(x) * magnitude(y))
  }

  def dotProduct(x: Vector[Float], y: Vector[Float]): Float = {
    (for ((a, b) <- x zip y) yield a * b) sum
  }

  def magnitude(x: Vector[Float]): Float = {
    math.sqrt(x map (i => i * i) sum).toFloat
  }

  def extractTiles(outputPath: String, sourceFile: String): Unit = {
    val img: BufferedImage = ImageIO.read(new File(sourceFile))
    val imageParser = new MultiDeviceParser(new JavaOCRCharParser)
    for ((img, i) <- imageParser.extractTileImages(img).view.zipWithIndex) {
      ImageIO.write(img, "png", new File("%s/tile_%d.png".format(outputPath, i)))
    }
  }

}
