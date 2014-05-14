package me.rickychang.lpb.replay

import scala.io.Source
import scala.util.parsing.json.JSON
import java.io.{InputStream, IOException}
import java.net.URL

object ReplayUtils {
  def fetchJson(url: String): String = {
    var input: InputStream = null
    try {
      input = new URL(url.replaceAllLiterally("#", "")).openStream
      Source.fromInputStream(input).mkString
    } catch {
      case ex: IOException => ""
    } finally {
      input.close
    }
  }

  /** Returns a set of words that have already been played.
   *
   *  The incoming format is in uppercase; I assume that the output is also.
   *
   *  The incoming JSON is of the form
   *    {"players" : [{"username" : "Opponent"},
   *                  {"username" : "Player"}],
   *     "letters" : "ABCDEFGHIJKLMNOPQRSTUVWXY",
   *     "perspective" : 1.0,
   *     "columns" : 5.0,
   *     "turns" : [{"w" : "WORD", "t" : 0.0},
   *                {"w" : "CHEATS", "t" : 1.0},
   *                ...
   *               ]}
   *
   *  Beware, lots of casting ahead.
   */
  def playedWords(json: String): Set[String] = {
    try {
      JSON.parseFull(json).get.asInstanceOf[Map[Any, Any]]
      .get("turns").get.asInstanceOf[List[Any]]
      .map(_.asInstanceOf[Map[String, String]].get("w"))
      .map(_.get).toSet
    } catch {
      case _: NoSuchElementException | _: ClassCastException => Set.empty
    }
  }
}
