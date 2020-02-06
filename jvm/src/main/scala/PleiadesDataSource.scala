package edu.holycross.shot.pleiades

import scala.io._

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter




object PleiadesDataSource extends LogSupport {
  Logger.setDefaultLogLevel(LogLevel.INFO)


  def parseNamesUrl(url: String) : PleiadesNames = {
    val lines = Source.fromURL(url).getLines.toVector
    PleiadesNames.parseNamesCex(lines)
  }

  def parseNamesFile(fName: String) : PleiadesNames = {
    val lines = Source.fromFile(fName).getLines.toVector
    PleiadesNames.parseNamesCex(lines)
  }


  /** Parse records in a CEX-formatted dump of Pleiades places into
  * a [[PleiadesPlaces]] construction.
  *
  * @param url Name of file with Pleiades CSV data.
  *
  */
  def parsePlacesUrl(url: String) : PleiadesPlaces = {
    val lines = Source.fromURL(url).getLines.toVector
    PleiadesPlaces.parsePlacesCex(lines.tail)
  }


  /** Parse records in a CEX-formatted dump of Pleiades places into
  * a [[PleiadesPlaces]] construction.
  *
  * @param fName Name of file with Pleiades CSV data.
  *
  */
  def parsePlacesFile(fName: String) : PleiadesPlaces = {
    val lines = Source.fromFile(fName).getLines.toVector
    PleiadesPlaces.parsePlacesCex(lines.tail)
  }
}
