package edu.holycross.shot.pleiades

import org.scalatest.FlatSpec



class PleiadesPlaceSpec extends FlatSpec {

  val cex = """Becker, J., T. Elliott#13.4119837, 42.082885, 13.4119837, 42.082885#413005#2016-11-04T16:36:09Z#jbecker, thomase#1#The post-Roman settlement at Alba Fucens became an administrative center ca. A.D. 870 and remained significant as a center in Marsica until ca. 1143. The area was later subsumed by the Duchy of Spoleto.#{"type": "Point", "coordinates": [13.4119837, 42.082885]}#settlement###48210385#precise#1453#640#2016-11-08T21:58:28Z#/places/48210385#42.082885#42.082885,13.4119837#13.4119837##M#mediaeval-byzantine#640.0,1453.0#Borgo Medievale#ece5760c4c6d42c1a331aad543c4ecc4"""

  "A PleiadesPlace" should "be constructable from cex" in  {
    val place = PleiadesPlace(cex)
    place match {
      case plc: PleiadesPlace => assert(true)
      case _ => fail("Did not create a PleiadesPlace from cex.")
    }
  }
}
