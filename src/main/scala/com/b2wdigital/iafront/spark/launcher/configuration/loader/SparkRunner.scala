package com.b2wdigital.iafront.spark.launcher.configuration.loader

import java.util.concurrent.CountDownLatch

import com.b2wdigital.iafront.spark.launcher.SparkConstants
import com.b2wdigital.iafront.spark.launcher.configuration.entities
import com.b2wdigital.iafront.spark.launcher.configuration.entities.PipelineConfiguration
import com.b2wdigital.iafront.spark.launcher.listeners.ConsoleListener
import org.apache.spark.launcher.SparkLauncher

object SparkRunner {

  def runFromConfig(configuration:PipelineConfiguration) = {
    configuration.applications.foreach({
      application =>
        val countDownLatch = new CountDownLatch(1)
        val listener = new ConsoleListener(countDownLatch)
        val launcherWithConfig: SparkLauncher = createLauncher(application)
        launcherWithConfig.startApplication(listener)
        countDownLatch.await()
    })
  }

  private def createLauncher(application: entities.ApplicationConfiguration) = {
    val launcherWithoutConfig =
      new SparkLauncher()
        .setMaster(application.master.get)
        .setMainClass(application.mainClass)
        .setAppResource(application.appResource)
        .addAppArgs(application.appArgs.get: _*)
        .setConf("spark.driver.bindAddress", SparkConstants.sparkDriverBindAddress)
        .setConf("spark.blockManager.port", SparkConstants.sparkBlockManagerPort)
        .setConf("spark.driver.port", SparkConstants.sparkDriverPort)

    application.confs match {
      case Some(configs) => applyConf(launcherWithoutConfig, configs)
      case None => launcherWithoutConfig
    }
  }

  @scala.annotation.tailrec
  private def applyConf(launcher:SparkLauncher, configs:Map[String, String]):SparkLauncher = {
    configs.headOption match {
      case Some((key, value)) =>
        val launcherConfigured = launcher.setConf(key, value.toString)
        applyConf(launcherConfigured, configs.tail)
      case None => launcher
    }
  }
}