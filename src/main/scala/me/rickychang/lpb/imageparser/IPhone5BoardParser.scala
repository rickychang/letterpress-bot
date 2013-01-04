package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage
import scala.collection.mutable.StringBuilder
import ParserUtil.TilesPerRowColumn
import ParserUtil.getResizedDimension
import ParserUtil.getTileWidthHeight
import ParserUtil.resizeImage
import me.rickychang.lpb.board.TileState
import me.rickychang.lpb.board.GameBoard

// TODO: Abstract lots of this code to a super class that can be reused
// TODO: Add support for iphone 4 and ipad screenshots
// TODO: Make this an object with methods for extracting tiles, generating board.
class IPhone5BoardParser(sourceImage: BufferedImage, charParser: TileCharParser, stateParser: TileStateParser) {
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
  
  val tileStates: List[TileState] = tileImages.map{ stateParser.extractColor(_) }
  
  val boardTiles: List[(Char, TileState, Int)] = {
    (tileChars, tileStates, tileChars.indices).zipped.toList
  }
  
  val gameBoard = new GameBoard(boardTiles)
  
  override def toString = {
    val buffer = new StringBuilder
    for (i <- 0 until tileChars.length) {
      buffer.append("%s%s ".format(tileChars(i), tileStates(i)))
      if ((i + 1) % TilesPerRowColumn == 0) buffer.replace(buffer.length - 1, buffer.length, "\n")
    }
    buffer.stripLineEnd
  }
  
}

object IPhone5BoardParser {
  val BoardYOffsetRatio = 0.43661971830986f
  val ScreenshotAspectRatio = 0.56338028169014f
}