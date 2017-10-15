name := """www.gray.systems"""
organization := "systems.gray"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.1"
libraryDependencies += "com.google.guava" % "guava" % "20.0"
libraryDependencies += "org.jsoup" % "jsoup" % "1.10.3"
