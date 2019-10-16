package com.b2wdigital.iafront.spark.launcher.configuration

import org.scalatest.{Matchers, WordSpec}

import scala.io.Source

class ConfigurationsLoaderTest extends WordSpec with Matchers {

  private val testSubmit1 = yamlSource("/test_parameters.yaml")
  val applicationNamesList = List("application1", "application2")
  val applicationMainClassList = List("com.test.example.MainClass1", "com.test.example.MainClass2")

  val application1FileList = List("app1_file1.txt", "app1_file2.txt", "app1_file3.csv")
  val application2FileList = List("app2_file1.txt", "app2_file2.csv")
  private val configurations = new ConfigurationsLoader(testSubmit1)

  private val pipelineConfig = configurations.pipelineConfiguration
  private val app1Configuration = pipelineConfig.applications.filter(_.name == "application1").head
  private val app2Configuration = pipelineConfig.applications.filter(_.name == "application2").head

  private def yamlSource(sourcePath:String):String = {
    val fileStream = getClass.getResourceAsStream(sourcePath)
    Source.fromInputStream(fileStream).getLines.mkString("\n")
  }

  "PipelineConfiguration" should {

    "should return list of names" in {
      val result = pipelineConfig.applications.map {
        application =>
          application.name
      }
      result shouldBe applicationNamesList
    }

    "should return list of mainClasses" in {
      val result = pipelineConfig.applications.map {
        application =>
          application.mainClass
      }
      result shouldBe applicationMainClassList
    }
  }

  "ApplicationConfiguration" should {
    "files contain list of files for application1" in {
      app1Configuration.files shouldBe Some(application1FileList)
    }

    "Return no application file list" in {
      app2Configuration.files shouldBe None
    }

    "appArgs should return list for application1 and None for application2" in {
      app1Configuration.appArgs shouldBe
        Some(List("-c", "identificador=Eletroportáteis",
        "-s", "/path/to/source",
        "-c", "grupo=2",
        "-o", "/path/to/destination"))

      app2Configuration.appArgs shouldBe None
    }

    "jars should return list for application1 and None for application2" in {
      app1Configuration.jars shouldBe
        Some(List("app1.jar", "app2.jar"))
      app2Configuration.jars shouldBe None
    }

    "pyFiles should return list for application1 and None for application2" in {
      app1Configuration.pyFiles shouldBe
        Some(List("file1.py", "file2.py", "file3.py"))
      app2Configuration.pyFiles shouldBe None
    }

    "sparkArgs should return list for application1 and None for application2" in {
      app1Configuration.sparkArgs shouldBe
        Some(List("--supervise"))
      app2Configuration.sparkArgs shouldBe None
    }

    "directory should return string for application1 and None for application2" in {
      app1Configuration.directory shouldBe Some("/path/to/workdir")
      app2Configuration.directory shouldBe None
    }

    "directory should return boolean for application1 and None for application2" in {
      app1Configuration.redirectError shouldBe Some(false)
      app2Configuration.redirectError shouldBe None
    }

    "appResource should return string for application1 and None for application2" in {
      app1Configuration.appResource shouldBe "mainResource.jar"
      app2Configuration.appResource shouldBe "mainResource2.jar"
    }

    "confs should return map for application1 and None for application2" in {
      app1Configuration.confs shouldBe Some(Map(
        "spark.fs.s3a.impl"-> "org.apache.spark.fs.S3AFileSystem",
        "spark.fs.gs.impl"-> "org.apache.spark.gs.GSFileSystem"))
      app2Configuration.confs shouldBe None
    }

    "deployMode should return string for application1 and None for application2" in {
      app1Configuration.deployMode shouldBe Some("cluster")
      app2Configuration.deployMode shouldBe None
    }

    "master should return string for application1 and None for application2" in {
      app1Configuration.master shouldBe Some("local[*]")
      app2Configuration.master shouldBe None
    }

    "verbose should return boolean for application1 and None for application2" in {
      app1Configuration.verbose shouldBe Some(true)
      app2Configuration.verbose shouldBe None
    }

  }

}
