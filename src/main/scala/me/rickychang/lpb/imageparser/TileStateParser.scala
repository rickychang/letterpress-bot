package me.rickychang.lpb.imageparser

import java.awt.Color
import java.awt.image.BufferedImage
import me.rickychang.lpb.board.Free
import me.rickychang.lpb.board.OpponentDefended
import me.rickychang.lpb.board.OpponentOccupied
import me.rickychang.lpb.board.PlayerDefended
import me.rickychang.lpb.board.PlayerOccupied
import me.rickychang.lpb.board.TileColors._
import me.rickychang.lpb.board.TileState
import me.rickychang.lpb.imageparser.ParserUtil._
import me.rickychang.lpb.board.BoardThemeType._

object TileStateParser {

  private val lightColorToStateMap = {
    LightFreeColors.map(genPair(Free)) ++
    LightPlayerOccupiedColors.map(genPair(PlayerOccupied)) ++
    LightPlayerDefendedColors.map(genPair(PlayerDefended)) ++
    LightOpponentOccupiedColors.map(genPair(OpponentOccupied)) ++
    LightOpponentDefendedColors.map(genPair(OpponentDefended))
  } toMap
  
  private val darkColorToStateMap = {
   DarkFreeColors.map(genPair(Free)) ++
   DarkPlayerOccupiedColors.map(genPair(PlayerOccupied)) ++
   DarkPlayerDefendedColors.map(genPair(PlayerDefended)) ++
   DarkOpponentOccupiedColors.map(genPair(OpponentOccupied)) ++
   DarkOpponentDefendedColors.map(genPair(OpponentDefended))
  } toMap

  private val shortNamesToState = {
    val states = List(Free, PlayerOccupied, PlayerDefended, OpponentOccupied, OpponentDefended)
    states map { s => (s.shortName, s) } toMap
  }

  private def genPair(s: TileState) = (c: Color) => (c, s)

  def getState(themeType: BoardThemeType, tileImage: BufferedImage): TileState = {
    // TODO: clean this up using pattern matching.
    if (themeType == Dark) {
      val hist = new ColorHistogram(tileImage)
      darkColorToStateMap(normalizeColor(DarkColors, hist.dominantColor))
    }
    else {
      val hist = new ColorHistogram(tileImage)
      lightColorToStateMap(normalizeColor(LightColors, hist.dominantColor))
    }
  }

  def getState(shortName: Char): Option[TileState] = {
    shortNamesToState.get(shortName)
  }

}
