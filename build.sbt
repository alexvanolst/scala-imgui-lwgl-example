import Dependencies._

ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "imgui-lwjgl-example",
    resolvers += "Bintray" at "https://dl.bintray.com/kotlin/kotlin-dev",
    resolvers += "Sonatype" at "https://oss.sonatype.org/content/repositories/snapshots/",
    resolvers += "Jitpack" at "https://jitpack.io",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.github.kotlin-graphics.imgui" % "imgui" % "v1.74",
    libraryDependencies += "com.github.kotlin-graphics.imgui" % "imgui-gl" % "v1.74",
    libraryDependencies += "com.github.kotlin-graphics.imgui" % "imgui-glfw" % "v1.74",
    libraryDependencies ++= {
      val version = "3.2.3"
      val os = "windows" // TODO: Change to "linux" or "macos" if necessary
      Seq(
        "lwjgl",
        "lwjgl-glfw",
        "lwjgl-opengl",
        "lwjgl-jemalloc",
        "lwjgl-stb"
        // TODO: Add more modules here
      ).flatMap { module =>
        {
          Seq(
            "org.lwjgl" % module % version,
            "org.lwjgl" % module % version classifier s"natives-$os"
          )
        }
      }
    },
    libraryDependencies += "com.github.kotlin-graphics.uno-sdk" % "uno-core" % "c8a3099e8f5d335341df4010e8e7c20589317dfd",
    libraryDependencies += "com.github.kotlin-graphics.uno-sdk" % "uno-gl" % "c8a3099e8f5d335341df4010e8e7c20589317dfd",
    libraryDependencies += "com.github.kotlin-graphics" % "gln" % "880960aeaf813f72242f03458cf44e606d4c05f0",
    libraryDependencies += "com.github.kotlin-graphics.glm" % "glm" % "1b4ac18dd1a3c23440d3f33596688aac60bc0141",
    libraryDependencies += "com.github.kotlin-graphics" % "kool" % "fcf04b2c03b8949d9d9a8b0a580082e927903510",

  )
