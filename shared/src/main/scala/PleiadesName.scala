package edu.holycross.shot.pleiades



import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

import scala.scalajs.js.annotation._

@JSExportTopLevel("PleiadesName") case class PleiadesName(pleiadesId: BigDecimal, name: String) extends LogSupport {

}

object PleiadesName extends LogSupport {

  def apply(delimitedText: String) : PleiadesName = {
    val cols = delimitedText.replaceAll("##","# #").split("#").toVector
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
}
