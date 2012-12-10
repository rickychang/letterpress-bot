package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage
import scala.collection.mutable.StringBuilder
import ParserUtil._

// TODO: Abstract lots of this code to a super class that can be reused by
//       other board parsing classes
class IPhone5BoardParser(sourceImage: BufferedImage, charParser: TileCharParser) {
  import IPhone5BoardParser._

  val boardYOffset = (sourceImage.getHeight * BoardYOffsetRatio).toInt
  val boardWidth = getResizedDimension(sourceImage.getWidth)
  val boardHeight = getResizedDimension(sourceImage.getHeight - boardYOffset)
 
  val tileWidth = getTileWidthHeight(boardWidth)
  val tileHeight = getTileWidthHeight(boardHeight)
  
  val boardImage: BufferedImage = {
    val origBoardWidth = sourceImage.getWidth
    val origBoardHeight = sourceImage.getHeight - boardYOffset
    val origBoard = sourceImage.getSubimage(0, boardYOffset, origBoardWidth, origBoardHeight)
    resizeImage(origBoard, boardWidth, boardHeight)
  }

  val tileImages: List[BufferedImage] = {
    for {
      y <- 0 until boardHeight by tileHeight
      x <- 0 until boardWidth by tileWidth
    } yield boardImage.getSubimage(x, y, tileWidth, tileHeight)
  } toList
  
  val tileChars: List[Char] = tileImages.map{ charParser.extractChar(_).charAt(0) }
  
  override def toString = {
    val buffer = new StringBuilder
    for (i <- 0 until tileChars.length) {
      buffer += tileChars(i)
      if ((i + 1) % TilesPerRowColumn == 0) buffer += '\n'
    }
    buffer.stripLineEnd
  }
  
}

object IPhone5BoardParser {
  val BoardYOffsetRatio = 0.43661971830986f
  val ScreenshotAspectRatio = 0.56338028169014f
}