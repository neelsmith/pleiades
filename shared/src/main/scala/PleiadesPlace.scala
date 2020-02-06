package edu.holycross.shot.pleiades



import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

import scala.scalajs.js.annotation._

@JSExportTopLevel("PleiadesPlace")
case class PleiadesPlace(
  pleiadesId: BigDecimal,
  description: String,
  pointOption: Option[GeoPoint]) extends LogSupport{


}
