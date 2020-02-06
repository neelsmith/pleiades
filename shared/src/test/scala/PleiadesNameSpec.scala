package edu.holycross.shot.pleiades

import org.scalatest.FlatSpec

class PleiadesNameSpec extends FlatSpec {

  val cex = """Spann, P., R. Warner, R. Talbert, T. Elliott, S. Gillies#-3.606772, 39.460299, -3.606772, 39.460299#2010-09-24T19:02:22Z#P.O. Spann###{"type": "Point", "coordinates": [-3.606772, 39.460299]}#consabura#precise#640#-330#2011-09-05T20:57:22Z###Consabura#/places/265876/consabura#/places/265876#39.460299#39.460299,-3.606772#-3.606772##HRL#hellenistic-republican,roman,late-antique#-330.0,640.0#Consabura#fc0ee157ff11ce6d2e72cd7c5df06fee"""


  "A PleiadesName" should "be constructable from a delimited text record" in  {
    val name = PleiadesName(cex)
    name match {
      case pn: PleiadesName => assert(true)
      case _ => fail("Did not create a PleiadesName from cex.")
    }
  }
}
