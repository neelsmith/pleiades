package edu.holycross.shot.pleiades

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

import scala.scalajs.js.annotation._

@JSExportTopLevel("PleiadesNames") case class PleiadesNames(places: Vector[PleiadesName]) extends LogSupport {


}

object PleiadesNames extends LogSupport {

  /**  Parse a Vector of Pleiades delimited-text records into
  * a [[PleiadesNames]] object.
  *
  * @param lines Vector of delimited-text lines.
  */
  def parseNamesCex(lines: Vector[String]) : PleiadesNames = {
    val data = lines.tail.filterNot(_.contains("errata"))
    info("Parsing " + data.size + " pleiades name records.")
    val names = for (record <- data) yield {
      PleiadesName(record)
      /*
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
      }*/
    }
    val ok = names.filterNot(_.pleiadesId == -1)
    info("Parsed  " + ok.size + " names records.")
    PleiadesNames(ok)
  }
}
