package edu.holycross.shot.pleiades

import scala.io._

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter



/** Factory object for creating [[PleiadesNames]] and
* [[PleiadesPlaces]] objects from various delimited-text sources. */
object PleiadesDataSource extends LogSupport {
  Logger.setDefaultLogLevel(LogLevel.INFO)


  /** Load remote CEX data for [[PleiadesNames]] and [[PleiadesPlaces]] and
  * create a new [[Pleiades]] collection.
  *
  * @param placesUrl URL for places CEX data.
  * @param namesUrl URL for names CEX data.
  */
  def loadFromUrls(
    placesUrl: String = "https://raw.githubusercontent.com/neelsmith/pleiades/master/jvm/src/test/resources/pleiades-places-20200129.cex",
    namesUrl: String = "https://raw.githubusercontent.com/neelsmith/pleiades/master/jvm/src/test/resources/pleiades-names-20200205.cex"
  ): Pleiades = {
    info("Loading Pleiades place data...")
    val places = parsePlacesUrl(placesUrl)
    info("Loading Pleiades name data...")
    val names = parseNamesUrl(namesUrl)
    Pleiades(places, names)
  }

  /** Parse CEX-formatted dump of Pleiades project names data.
  *
  * @param url URL to download data from.
  */
  def parseNamesUrl(url: String) : PleiadesNames = {
    val lines = Source.fromURL(url).getLines.toVector
    PleiadesNames.parseNamesCex(lines)
  }

  /** Parse CEX-formatted dump of Pleiades project names data.
  *
  * @param fName Name of file with delimited-text data.
  */
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
