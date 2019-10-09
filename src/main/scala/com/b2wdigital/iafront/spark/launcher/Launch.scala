package com.b2wdigital.iafront.spark.launcher

import org.apache.spark.launcher.{SparkAppHandle, SparkLauncher}

object Launch extends App {

  val appResource = ???
  val mainClass = ???
  val master = ???
  val confs:Map[String, String] = ???

  val launcher = new SparkLauncher()
    .setAppResource(appResource)
    .setMainClass(mainClass)
    .setMaster(master)
  for ((key, value) <- confs.toList) {
    launcher.setConf(key, value)
  }
}
