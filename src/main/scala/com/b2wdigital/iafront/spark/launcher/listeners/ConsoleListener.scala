package com.b2wdigital.iafront.spark.launcher.listeners

import org.apache.spark.launcher.SparkAppHandle
import org.apache.spark.launcher.SparkAppHandle.Listener
import java.util.concurrent.CountDownLatch

class ConsoleListener(countDownLatch:CountDownLatch) extends Listener {

  override def stateChanged(handle: SparkAppHandle): Unit = {
    println(s"Spark App Id [${handle.getAppId}] State Changed. State [${handle.getState}]")

    if(handle.getState == SparkAppHandle.State.FAILED)
      countDownLatch.countDown()
  }

  override def infoChanged(handle: SparkAppHandle): Unit = {
    val appState = handle.getState
    println(s"Spark App Id [${handle.getAppId}] State Changed. State [${handle.getState}]")

    if (appState != null && appState.isFinal)
      countDownLatch.countDown()
  }
}