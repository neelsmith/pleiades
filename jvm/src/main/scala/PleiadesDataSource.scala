package edu.holycross.shot.pleiades

import scala.io._

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter




object PleiadesDataSource extends LogSupport {
  Logger.setDefaultLogLevel(LogLevel.INFO)

  def parseNamesCex(fName: String) : Vector[PleiadesName]= {
    val lines = Source.fromFile(fName).getLines.toVector
    val header = lines.head
    val data = lines.tail.filterNot(_.contains("errata"))
    info("Parsing " + data.size + " pleiades name records.")
    val names = for (record <- data) yield {
      val cols = record.replaceAll("##","# #").split("#").toVector
      val description = cols(6)

      if (cols.size != 26) {
        warn("ERROR IN INPUT LINE: " + cols.size + " columns")
        PleiadesName(-1, "ERROR ON INPUT: " + cols)

      } else {
        if (cols(16).contains("/places") == false) {
            warn("Ignoring bad ID value " + cols(16))
            PleiadesName(-1, "Bad ID value: " + cols(16))
        } else {
          val placeName = cols(24)
          val numeric =  cols(16).replaceAll("/places/", "")
          debug("Interpret ID "+ numeric)
          val id = BigDecimal(numeric)

          PleiadesName(id, placeName)
        }
      }
    }
    val ok = names.filterNot(_.pleiadesId == -1)
    info("Parsed  " + ok.size + " names records.")
    ok
  }



  /** Parse records in a CEX-formatted dump of Pleiades places into
  * a [[PleiadesData]] construction.
  *
  * @param fName Name of file with Pleiades CSV data.
  *
  */
  def parsePlacesFile(fName: String) : PleiadesData = {
    val lines = Source.fromFile(fName).getLines.toVector
    val header = lines.head
    PleiadesData.parsePlacesCex(lines.tail)
  }
}
