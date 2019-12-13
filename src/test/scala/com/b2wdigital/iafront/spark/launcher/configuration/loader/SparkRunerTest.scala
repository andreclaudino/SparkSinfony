package com.b2wdigital.iafront.spark.launcher.configuration.loader

import javax.ws.rs.NotFoundException
import org.scalatest.{FlatSpec, Matchers}

class SparkRunerTest extends FlatSpec with Matchers {

  "replaceVariable" should "change values" in {
    val imageName = "registry.b2w.io/$PROJECT_ID/$IMAGE_NAME:$VERSION"
    val enviromentMap =  Map(
      "PROJECT_ID"-> "spark-sinfony",
      "IMAGE_NAME"-> "sinfony-runner",
      "VERSION"-> "latest"
    )

    val transformed = SparkRunner.applyEnvironment(imageName, enviromentMap)

    transformed shouldBe "registry.b2w.io/spark-sinfony/sinfony-runner:latest"
  }

  "replaceVariable" should "raise exception if variable not found" in {
    val imageName = "registry.b2w.io/$IMAGE_NAME"
    val enviromentMap =  Map(
      "PROJECT_ID"-> "spark-sinfony",
      "VERSION"-> "latest"
    )

    an[NotFoundException] should be thrownBy (SparkRunner.applyEnvironment(imageName, enviromentMap))
  }

  "replaceVariable" should "do nothing if there is no variable" in {
    val imageName = "registry.b2w.io/spark-sinfony/sinfony-runner:latest"
    val enviromentMap =  Map(
      "PROJECT_ID"-> "spark-sinfony",
      "IMAGE_NAME"-> "sinfony-runner",
      "VERSION"-> "latest"
    )

    val transformed = SparkRunner.applyEnvironment(imageName, enviromentMap)

    transformed shouldBe "registry.b2w.io/spark-sinfony/sinfony-runner:latest"
  }
}
