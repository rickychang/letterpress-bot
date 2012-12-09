package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage
import ParserUtil._

// TODO: Abstract lots of this code to a super class that can be reused by
//       other board parsing classes
class iPhone5BoardTwitterParser(sourceImage: BufferedImage) {
  import iPhone5TwitterBoardParser._

  val boardWidth = getResizedDimension(sourceImage.getWidth)
  val boardHeight = getResizedDimension(sourceImage.getHeight - BoardYOffset)
  val tileWidth = getTileWidthHeight(boardWidth)
  val tileHeight = getTileWidthHeight(boardHeight)
  
  val boardImage: BufferedImage = {
    val origBoardWidth = sourceImage.getWidth
    val origBoardHeight = sourceImage.getHeight - BoardYOffset
    val origBoard = sourceImage.getSubimage(0, BoardYOffset, origBoardWidth, origBoardHeight)
    resizeImage(origBoard, boardWidth, boardHeight)
  }

  val tileImages: List[BufferedImage] = {
    for {
      x <- 0 until boardWidth by tileWidth
      y <- 0 until boardHeight by tileHeight
    } yield boardImage.getSubimage(x, y, tileWidth, tileHeight)
  } toList
  
}

// TODO: Convert constants into functions that use relative dimensions 
//       of screenshots.  E.g. board offset is shouldn't be absolute
//       it should be relative so same code will work for scaled iPhone 5
//       images like twitter attachments or png screenshots directly from 
//       iOS
object iPhone5TwitterBoardParser {
  val BoardYOffset = 447
}