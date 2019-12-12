package com.b2wdigital.iafront.spark.launcher

object SparkDefaults {

  def sparkK8sDefaults = {
    Map(
      "spark.driver.bindAddress"-> sys.env.getOrElse("SPARK_DRIVER_BIND_ADDRESS", "0.0.0.0"),
      "spark.blockManager.port" -> "39121",
      "spark.driver.port" -> "3026",
      "spark.dynamicAllocation.enabled" -> "false",
      "spark.shuffle.service.enabled" -> "false",
      "spark.kubernetes.container.image.pullPolicy" -> "Always",
      "spark.kubernetes.submission.waitAppCompletion" -> "true",
      "deployMode" -> "cluster",
      "verbose" -> "true",
      "redirectError" -> "true",
      "spark.shuffle.service.enabled" -> "false"
    )
  }

}
