package me.rickychang.lpb.imageparser

import java.awt.Container
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Character
import java.util.ArrayList
import java.util.HashMap
import me.rickychang.lpb.imageparser.ParserUtil._
import net.sourceforge.javaocr.ocrPlugins.mseOCR.OCRScanner
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImageLoader
import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange
import javax.imageio.ImageIO

class JavaOCRCharParser(trainingImagesPath: String = DefaultTrainingImagePath) extends TileCharParser {
  // TODO: move this into a configuration file
  private val trainingImageFileNames = for {
    l <- ('A' to 'Z')
    s <- List("_d_128.png", "_w_128.png", "_d_228.png", "_w_228.png")
  } yield { (l, "%s/%s%s".format(trainingImagesPath, l, s)) }

  private val TrainingTileDimension = 128

  private val scanner: OCRScanner = new OCRScanner
  private val loader: TrainingImageLoader = new TrainingImageLoader
  private val trainingImageMap: HashMap[Character, ArrayList[TrainingImage]] = new HashMap[Character, ArrayList[TrainingImage]]()
  for ((l, f) <- trainingImageFileNames) {
    val imageURL = getClass.getResource(f)
    loader.load(new Container, ImageIO.read(imageURL), new CharacterRange(l, l), trainingImageMap, imageURL.getFile)
  }
  scanner.addTrainingImages(trainingImageMap)

  def extractChar(tileImage: BufferedImage): Char = {
    val charString = scanner.scan(tileImage, 0, 0, 0, 0, null)
    if (charString == null || charString.isEmpty || charString.length != 1)
      throw new InvalidImageException("Invalid tile image, unable to extract single character.")
    else charString.charAt(0)
  }

}