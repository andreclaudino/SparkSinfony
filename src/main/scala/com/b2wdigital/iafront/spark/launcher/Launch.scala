package com.b2wdigital.iafront.spark.launcher

import java.io.FileInputStream
import com.b2wdigital.iafront.spark.launcher.configuration.loader.{ConfigurationsLoader, SparkRunner}
import scala.io.Source
import com.b2wdigital.iafront.clparser.BaseParser

object Launch extends App {

  val commandLineParser = new BaseParser(args, Some("Spark Sinfony"))
  val filePath = commandLineParser.sourcePathOption.get

  val configuration =
    new ConfigurationsLoader(yamlSource(filePath), None)
      .pipelineConfiguration

  SparkRunner.runFromConfig(configuration)

  private def yamlSource(sourcePath: String): String = {
    val fileStream = new FileInputStream(sourcePath)
    Source.fromInputStream(fileStream).getLines.mkString("\n")
  }
}