package com.b2wdigital.iafront.spark.launcher

import java.io.FileInputStream
import java.util.concurrent.CountDownLatch

import com.b2wdigital.iafront.spark.launcher.configuration.ConfigurationsLoader
import com.b2wdigital.iafront.spark.launcher.listeners.ConsoleListener
import org.apache.spark.launcher.SparkLauncher

import scala.io.Source

object Launch extends App {

  val filePath = args(0)

  private def yamlSource(sourcePath:String):String = {
    val fileStream = new FileInputStream(sourcePath)
    Source.fromInputStream(fileStream).getLines.mkString("\n")
  }

  val configuration = new ConfigurationsLoader(yamlSource(filePath), None).pipelineConfiguration

  configuration.applications.foreach({
    application =>
      val countDownLatch = new CountDownLatch(1)

      val launcherWithoutConfig =
        new SparkLauncher()
        .setMaster(application.master.get)
        .setMainClass(application.mainClass)
        .setAppResource(application.appResource)
        .addAppArgs(application.appArgs.get:_*)
        .addSparkArg("--supervise")

      val launcherWithConfig =
        application.confs match {
          case Some(configs) => applyConf(launcherWithoutConfig, configs)
          case None => launcherWithoutConfig
        }

      val sparkApplication = launcherWithConfig.startApplication(new ConsoleListener(countDownLatch))

      println(sparkApplication.getAppId)
      countDownLatch.await()
  })

  @scala.annotation.tailrec
  def applyConf(launcher:SparkLauncher, configs:Map[String, String]):SparkLauncher = {
    configs.headOption match {
      case Some((key, value)) =>
        val launcherConfigured = launcher.setConf(key, value)
        applyConf(launcherConfigured, configs.tail)
      case None => launcher
    }
  }
}
