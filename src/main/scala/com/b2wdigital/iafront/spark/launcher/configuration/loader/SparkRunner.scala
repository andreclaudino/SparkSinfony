package com.b2wdigital.iafront.spark.launcher.configuration.loader

import java.util.concurrent.CountDownLatch

import com.b2wdigital.iafront.spark.launcher.SparkDefaults
import com.b2wdigital.iafront.spark.launcher.configuration.entities
import com.b2wdigital.iafront.spark.launcher.configuration.entities.PipelineConfiguration
import com.b2wdigital.iafront.spark.launcher.listeners.ConsoleListener
import javax.ws.rs.NotFoundException
import org.apache.spark.launcher.SparkLauncher

object SparkRunner {

  def runFromConfig(configuration:PipelineConfiguration):Unit = {
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
        .setMaster(application.master.getOrElse("k8s://kubernetes.default.svc"))
        .setMainClass(application.mainClass)
        .setAppResource(application.appResource)
        .addAppArgs(application.appArgs.get: _*)
        .setAppName(application.name)
        .setDeployMode("cluster")
        .setVerbose(false)
        .redirectError()

    val laucherWithDefaults = applyConf(launcherWithoutConfig, SparkDefaults.sparkK8s)

    application.confs match {
      case Some(configs) => applyConf(laucherWithDefaults, configs)
      case None => launcherWithoutConfig
    }
  }

  // TODO: Identificar símbolos como ${VARIABLE}  e $VARIABLE e substituir pelo valor da variável de ambiente VARIABLE
  private[loader] def applyEnvironment(value:String, envMap:Map[String,String]):String = {

    val re = """\$(\w*)""".r
    replaceVariable(value, re.findAllIn(value).matchData, envMap)
  }

  @scala.annotation.tailrec
  private def replaceVariable(value:String, iterator:Iterator[scala.util.matching.Regex.Match],
                                      environmentDict:Map[String, String]):String ={

    if(iterator.isEmpty)
      return value

    val key = iterator.next().matched
    val escapedKey = key.replace("$", "\\$")

    if(!environmentDict.keys.toArray.contains(key.tail)){
      throw new NotFoundException(s"key $key not found")
    }

    val newValue = value.replaceAll(escapedKey, environmentDict(key.tail))

    if(iterator.hasNext)
      replaceVariable(newValue, iterator, environmentDict)
    else
      newValue
  }

  @scala.annotation.tailrec
  private def applyConf(launcher:SparkLauncher, configs:Map[String, String]):SparkLauncher = {
    configs.headOption match {
      case Some((key, value)) =>
        val parsedValue = applyEnvironment(value.toString, SparkDefaults.envMap)
        val launcherConfigured = launcher.setConf(key, parsedValue)
        println(s"$key -> $parsedValue")
        applyConf(launcherConfigured, configs.tail)
      case None => launcher
    }
  }
}