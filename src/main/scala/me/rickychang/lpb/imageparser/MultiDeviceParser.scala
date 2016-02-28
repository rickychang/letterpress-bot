package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage
import scala.collection.immutable.List
import me.rickychang.lpb.board.BoardThemeType.BoardThemeType
import me.rickychang.lpb.board.GameBoard

class MultiDeviceParser(cParser: TileCharParser) extends BoardParser {

  val charParser = cParser

  private val deviceSpecificParsers: List[IOSDeviceParser] =
    List(new IPhone5Parser(cParser), new IPhone4Parser(cParser), new IPadParser(cParser), new IPhone6Parser(cParser))

  /**
   * Find most appropriate device-specific parser for a given image.
   */
  private def getParser(screenshot: BufferedImage): Option[BoardParser] = {
    val aRatio = screenshot.getWidth.toFloat / screenshot.getHeight.toFloat
    val (parser, aspectRatioDiff) =
      deviceSpecificParsers.map { p =>
        (p, math.abs(p.screenshotAspectRatio - aRatio))
      }.minBy(_._2)
    if (aspectRatioDiff / parser.screenshotAspectRatio > .005) None
    else Some(parser)
  }

  def extractBoardImage(screenshot: BufferedImage): BufferedImage = {
    getParser(screenshot) match {
      case Some(p) => p.extractBoardImage(screenshot)
      case None =>
        throw new InvalidImageException("Unable to find valid parser for image aspect ratio")
    }
  }

  def getThemeType(screenshot: BufferedImage): BoardThemeType = {
    getParser(screenshot) match {
      case Some(p) => p.getThemeType(screenshot)
      case None =>
        throw new InvalidImageException("Unable to find valid parser for image aspect ratio")
    }
  }

  def extractTileImages(screenshot: BufferedImage): List[BufferedImage] = {
    getParser(screenshot) match {
      case Some(p) => p.extractTileImages(screenshot)
      case None =>
        throw new InvalidImageException("Unable to find valid parser for image aspect ratio")
    }
  }

  def parseGameBoard(screenshot: BufferedImage): GameBoard = {
    getParser(screenshot) match {
      case Some(p) => p.parseGameBoard(screenshot)
      case None =>
        throw new InvalidImageException("Unable to find valid parser for image aspect ratio")
    }
  }
}
