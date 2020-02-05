package edu.holycross.shot.pleiades
import org.scalatest.FlatSpec



class PleiadesDataSpec extends FlatSpec {

  val pleiadesCex = "jvm/src/test/resources/pleiades-places-20200129.cex"

  "The PleiadesData object"  should "parse Pleiades places dump" in {
    val parsed = PleiadesData.parsePlacesFile(pleiadesCex)
  }


}
