package com.b2wdigital.iafront.spark.launcher

object SparkDefaults {
  def sparkK8s:Map[String, String] = {
    Map(
      "spark.blockManager.port" -> "39121",
      "spark.driver.port" -> "3026",
      "spark.dynamicAllocation.enabled" -> "false",
      "spark.shuffle.service.enabled" -> "false",
      "spark.kubernetes.container.image.pullPolicy" -> "Always",
      "spark.kubernetes.submission.waitAppCompletion" -> "true",
      "spark.shuffle.service.enabled" -> "false"
    )
  }


  def envMap:Map[String, String] = {
    sys.env
  }
}