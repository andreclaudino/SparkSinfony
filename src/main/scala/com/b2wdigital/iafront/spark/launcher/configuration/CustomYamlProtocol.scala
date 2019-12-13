package com.b2wdigital.iafront.spark.launcher.configuration

import net.jcazevedo.moultingyaml._
import com.b2wdigital.iafront.spark.launcher.configuration.entities._

class CustomYamlProtocol extends DefaultYamlProtocol {
  implicit val applicationConfigurationFormat = yamlFormat11(ApplicationConfiguration)
  implicit val pipelineConfigurationFormat = yamlFormat1(PipelineConfiguration)
}
