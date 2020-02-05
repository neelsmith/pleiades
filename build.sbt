name := "Pleiades utilities"

crossScalaVersions := Seq("2.12.4") //Seq("2.10.6","2.11.8", "2.12.4")

lazy val root = project.in(file(".")).
    aggregate(crossedJVM, crossedJS).
    settings(
      publish := {},
      publishLocal := {}

    )

lazy val crossed = crossProject.in(file(".")).
    settings(
      name := "pleiades",
      organization := "edu.holycross.shot",
      version := "0.3.2",
      licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html")),

      libraryDependencies ++= Seq(
        "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided",
        "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
        "org.wvlet.airframe" %%% "airframe-log" % "19.8.10"
      )
    ).
    jvmSettings(
    ).
    jsSettings(
      skip in packageJSDependencies := false,
      scalaJSUseMainModuleInitializer in Compile := true
    )
lazy val crossedJVM = crossed.jvm
lazy val crossedJS = crossed.js
//lazy val crossedJVM = crossed.jvm
//lazy val crossedJS = crossed.js.enablePlugins(ScalaJSPlugin)
