package me.rickychang.lpb.imageparser

import java.awt.Container
import java.awt.image.BufferedImage
import java.lang.Character
import java.util.ArrayList
import java.util.HashMap

import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange
import net.sourceforge.javaocr.ocrPlugins.mseOCR.OCRScanner
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImageLoader

import me.rickychang.lpb.imageparser.ParserUtil._

class JavaOCRCharParser(trainingImagesPath: String = DefaultTrainingImagePath) extends TileCharParser {
  private val scanner: OCRScanner = new OCRScanner
  private val loader: TrainingImageLoader = new TrainingImageLoader
  private val container: Container = new Container
  private val trainingImageMap: HashMap[Character, ArrayList[TrainingImage]] = new HashMap[Character, ArrayList[TrainingImage]]()
  for (f <- new java.io.File(getClass.getResource(trainingImagesPath).getFile()).listFiles.filter(_.getName.endsWith(".png"))) {
    val char = f.getName().split("_").head.charAt(0)
    loader.load(container, f.getAbsolutePath(), new CharacterRange(char, char), trainingImageMap)
  }
  scanner.addTrainingImages(trainingImageMap)

  def extractChar(tileImage: BufferedImage): String = {
    scanner.scan(tileImage, 0, 0, 0, 0, null)
  }

}