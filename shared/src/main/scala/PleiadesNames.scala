package edu.holycross.shot.pleiades

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

import scala.scalajs.js.annotation._

@JSExportTopLevel("PleiadesNames") case class PleiadesNames(names: Vector[PleiadesName]) extends LogSupport {

  /** Find (possibly empty) list of transcribed names
  * associated with a given Pleiades ID value.
  *
  * @param id ID value to look up.
  */
  def lookup(id: BigDecimal): Vector[String] = {
    names.filter(_.pleiadesId == id).map(_.name)
  }

  /** Find (possibly empty) list of Pleiades IDs
  * matching a given name.
  *
  * @param s Name to match.
  */
  def idsForName(s: String) : Vector[BigDecimal] = {
    names.filter(_.name == s).map(_.pleiadesId)
  }

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
    }
    val ok = names.filterNot(_.pleiadesId == -1)
    val bad = names.filter(_.pleiadesId == -1)
    if (bad.nonEmpty) {
      info("Failed on " + bad.size + " records.")
    }
    info("Successfully parsed  " + ok.size + " names records.")
    PleiadesNames(ok)
  }
}
