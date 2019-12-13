package com.b2wdigital.iafront.spark.launcher.configuration

package object entities {

  case class ApplicationConfiguration(
                       mainClass:String, name:String,
                       appArgs:Option[List[String]], files:Option[List[String]],
                       jars:Option[List[String]], pyFiles:Option[List[String]],
                       sparkArgs:Option[List[String]], directory:Option[String],
                       appResource:String, confs:Option[Map[String, String]],
                       master:Option[String])

  case class PipelineConfiguration(applications:List[ApplicationConfiguration])
}
