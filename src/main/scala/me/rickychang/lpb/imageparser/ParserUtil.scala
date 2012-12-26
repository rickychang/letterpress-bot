package me.rickychang.lpb.imageparser

import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

object ParserUtil {

  val TilesPerRowColumn = 5
  
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
   * Given an input directory and relative screenshot image filename, 
   * extract individual tile images from screenshot and write to disk 
   * as individual files in same directory
   */
  def extractTiles(path: String, sourceFile: String): Unit = {
      val img: BufferedImage = ImageIO.read(new File("%s/%s".format(path, sourceFile)))
      val tileParser: JavaOCRCharParser = new JavaOCRCharParser("/images/training")
      val imageParser = new IPhone5BoardParser(img, tileParser, ColorHistogramTileStateParser)
      for ((img,i) <- imageParser.tileImages.view.zipWithIndex) {
        ImageIO.write(img, "png", new File("%s/tile_%d.png".format(path, i)))
      }
  }
}