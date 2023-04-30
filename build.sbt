ThisBuild / version := "1.0"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "grpc-project"
  )

Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
)

libraryDependencies ++= Seq(
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
)

libraryDependencies ++= Seq(
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  //"io.netty" % "netty-tcnative-boringssl-static" % "2.0.20.Final",
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
)

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % "4.0.0",
  "org.scalikejdbc" %%  "scalikejdbc-config"  % "4.0.0",
  "org.postgresql" % "postgresql" % "42.6.0",
  "ch.qos.logback" % "logback-classic" % "1.4.6"
)

scalacOptions += "-Dorg.slf4j.simpleLogger.defaultLogLevel=INFO"

assembly/mainClass := Some("server.Server")

assembly/assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.deduplicate
}