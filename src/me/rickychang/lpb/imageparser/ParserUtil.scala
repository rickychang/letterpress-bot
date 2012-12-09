package me.rickychang.lpb.imageparser

import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.awt.RenderingHints

object ParserUtil {

  val TilesPerRowColumn = 5
  
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
    val scaledImage: BufferedImage  = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val graphics2D: Graphics2D = scaledImage.createGraphics()
    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    graphics2D.drawImage(originalImage, 0, 0, width, height, null)
    graphics2D.dispose()
    scaledImage
  }
}