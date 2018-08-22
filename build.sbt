name := "functionalStructuresInScala"

version := "0.1"

scalaVersion := "2.12.6"

resolvers ++= Seq(
  "Sonatype Public" at "https://oss.sonatype.org/content/groups/public/",
  "bintray/non" at "http://bintray.com/non/maven"
)

addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full)
addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.7")


// https://mvnrepository.com/artifact/com.github.mpilquist/simulacrum
libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.13.0"
