package com.b2wdigital.iafront.spark.launcher

object SparkConstants {
  val sparkBlockManagerPort = "39121"
  val sparkDriverPort = "3026"

  def sparkDriverBindAddress:String = sys.env.getOrElse("SPARK_DRIVER_BIND_ADDRESS", "0.0.0.0")
}