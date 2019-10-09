package com.b2wdigital.iafront.spark.launcher.configuration

package object entities {

  case class ApplicationConfiguration(
                       mainClass:String, name:String,
                       appArgs:Option[List[String]], files:Option[List[String]],
                       jars:Option[List[String]], pyFiles:Option[List[String]],
                       sparkArgs:Option[List[String]], directory:Option[String],
                       redirectError:Option[Boolean], appResource:Option[String],
                       confs:Option[Map[String, String]], deployMode:Option[String],
                       master:Option[String], verbose:Option[Boolean])
  case class PipelineConfiguration(applications:Option[List[ApplicationConfiguration]]=None)
}
