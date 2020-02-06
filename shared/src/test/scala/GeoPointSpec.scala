package edu.holycross.shot.pleiades

import org.scalatest.FlatSpec

class GeoPointSpec extends FlatSpec {

  "A GeoPoint"  should "offer comma-separated stringificaiton" in {
    val geoPoint = GeoPoint(BigDecimal("42"), BigDecimal("36"))
    assert (geoPoint.toString == "42,36")
  }

}
