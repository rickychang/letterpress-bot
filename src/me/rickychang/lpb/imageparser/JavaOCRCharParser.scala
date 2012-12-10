package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage
import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange
import net.sourceforge.javaocr.ocrPlugins.mseOCR.OCRScanner
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImageLoader
import net.sourceforge.javaocr.scanner.PixelImage
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage
import java.awt.Image
import java.awt.Frame
import java.util.HashMap
import java.util.ArrayList

class JavaOCRCharParser(trainingImagePath: String) extends TileCharParser {
  private val scanner: OCRScanner = new OCRScanner
  private val loader: TrainingImageLoader = new TrainingImageLoader
  private val frame: Frame = new Frame
  private val trainingImageMap: HashMap[Character, ArrayList[TrainingImage]] = new HashMap[Character, ArrayList[TrainingImage]]()

  for (f <- new java.io.File(trainingImagePath).listFiles.filter(_.getName.endsWith(".png"))) {
    val char = f.getName().split("_")(0).charAt(0)
    loader.load(frame, f.getAbsolutePath(), new CharacterRange(char, char), trainingImageMap)
  }
  scanner.addTrainingImages(trainingImageMap)

  def extractChar(tileImage: BufferedImage): String = {
    scanner.scan(tileImage, 0, 0, 0, 0, null)
  }

}