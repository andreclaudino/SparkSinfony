package com.b2wdigital.iafront.spark.launcher.configuration.loader

import com.b2wdigital.iafront.spark.launcher.configuration.CustomYamlProtocol
import com.b2wdigital.iafront.spark.launcher.configuration.entities._
import net.jcazevedo.moultingyaml._

class ConfigurationsLoader(yamlCode:String, overWrites:Option[Map[String, Any]] = None) extends CustomYamlProtocol {
  private val parsedYaml = yamlCode.parseYaml

  def pipelineConfiguration:PipelineConfiguration = parsedYaml.convertTo[PipelineConfiguration]
}