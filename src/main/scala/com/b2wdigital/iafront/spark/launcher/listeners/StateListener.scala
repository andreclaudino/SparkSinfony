package com.b2wdigital.iafront.spark.launcher.listeners

import org.apache.spark.launcher.SparkAppHandle
import org.apache.spark.launcher.SparkAppHandle.Listener
import java.util.concurrent.CountDownLatch

class StateListener(countDownLatch:CountDownLatch) extends Listener {

  override def stateChanged(handle: SparkAppHandle): Unit = {
    println(s"Spark App Id [${handle.getAppId}] State Changed. State [${handle.getState}]")

    handle.getState match {
      case SparkAppHandle.State.FAILED | SparkAppHandle.State.LOST | SparkAppHandle.State.FINISHED | SparkAppHandle.State.KILLED =>
        countDownLatch.countDown()
      case state =>
        println(s"Spark App Id [${handle.getAppId}] State Changed. State [$state]")
    }
  }

  override def infoChanged(handle: SparkAppHandle): Unit = {
    val appState = handle.getState
    println(s"Spark App Id [${handle.getAppId}] State Changed. State [${handle.getState}]")

    if (appState != null && appState.isFinal)
      countDownLatch.countDown()
  }
}