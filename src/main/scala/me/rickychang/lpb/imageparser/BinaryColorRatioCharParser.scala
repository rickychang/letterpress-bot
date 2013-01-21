package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import ParserUtil._

class BinaryColorRatioCharParser(trainingImagesPath: String = DefaultTrainingImagePath) extends TileCharParser {

  private val NumRegionRowColumns = 5

  private val trainingImageFileNames = for {
    l <- ('A' to 'Z')
    s <- List("_w_128.png", "_w_228.png")
  } yield { (l, "%s/%s%s".format(trainingImagesPath, l, s)) }

  val trainingVectors: List[(Vector[Float], Char)] = trainingImageFileNames.map {
    case (c, f) =>
      val tileImg = ImageIO.read(getClass.getResource(f))
      (getFeatureVector(tileImg), c)
  }.toList

  def getFeatureVector(tileImage: BufferedImage): Vector[Float] = {
    val cropped = ParserUtil.getBinaryCroppedTile(tileImage)
    val tWidth = (cropped.getWidth + NumRegionRowColumns - 1) / NumRegionRowColumns * NumRegionRowColumns
    val tHeight = (cropped.getHeight + NumRegionRowColumns - 1) / NumRegionRowColumns * NumRegionRowColumns
    val resized = ParserUtil.resizeImage(cropped, tWidth, tHeight)
    val regionWidth = tWidth / NumRegionRowColumns
    val regionHeight = tHeight / NumRegionRowColumns
    val regions = {
      for {
        y <- 0 until tHeight by regionHeight
        x <- 0 until tWidth by regionWidth
      } yield ParserUtil.convertToBinaryImage(resized.getSubimage(x, y, regionWidth, regionHeight))
    }
    val regionBlkPercents = regions.map { img =>
      val histogramMap = new ColorHistogram(img).histogramMap
      val blackCount = histogramMap.getOrElse(BlackInt, 0)
      val whiteCount = histogramMap.getOrElse(WhiteInt, 0)
      blackCount.toFloat / (whiteCount.toFloat + blackCount.toFloat)
    }
    val featureVector = Vector[Float]() ++ regionBlkPercents
    featureVector :+ (tWidth.toFloat / tHeight.toFloat)
  }

  def extractChar(tileImage: BufferedImage): Char = {
    val v = getFeatureVector(tileImage)
    trainingVectors.map(e => (e._2, cosineSimilarity(v, e._1))).maxBy(_._2)._1
  }

}