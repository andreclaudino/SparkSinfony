package com.b2wdigital.iafront.spark.launcher.configuration

import net.jcazevedo.moultingyaml._
import com.b2wdigital.iafront.spark.launcher.configuration.entities._

class ConfigurationsLoader(yamlCode:String, overWrites:Option[Map[String, Any]] = None) extends CustomYamlProtocol {
  private val parsedYaml = yamlCode.parseYaml

  def pipelineConfiguration:PipelineConfiguration =
    parsedYaml.convertTo[PipelineConfiguration]
}