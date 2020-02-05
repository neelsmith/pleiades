package edu.holycross.shot.pleiades


import scala.scalajs.js
import js.annotation.JSExport

@JSExport case class GeoPoint(x: BigDecimal, y: BigDecimal) {

  override def toString = {
    x.toString + "," + y.toString
  }
}
