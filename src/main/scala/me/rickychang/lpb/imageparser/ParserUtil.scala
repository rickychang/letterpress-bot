package me.rickychang.lpb.imageparser

import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File

import scala.math.pow
import scala.math.sqrt

import javax.imageio.ImageIO

object ParserUtil {

  val TilesPerRowColumn = 5
  
  val TilesPerBoard = 25
  
  val DefaultTrainingImagePath = "/images/training"
  
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
}
