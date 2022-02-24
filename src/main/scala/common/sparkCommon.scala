package common

import org.apache.spark.sql.SparkSession
import org.slf4j.LoggerFactory

object sparkCommon {

  private val Logger = LoggerFactory.getLogger(getClass.getName)

  def createSparkSession(): Option[SparkSession] = {
    // Create a Spark Session
    Logger.info("Creating Spark Session")
    try {
      Logger.info("createSparkSession() Method started")
      System.setProperty("hadoop.dir.home", "E://DataSamples//hadoop")

      val spark = SparkSession
        .builder()
        .appName("Ecomm")
        .config("spark.master", "local")
        .enableHiveSupport()
        .getOrCreate()

      Logger.info("SparkSession Created Successfully")
      Some(spark)
    }
    catch {
      case e: Exception =>
        Logger.error("Exception Occured in sparkSessionBuilder method : " + e.printStackTrace())
        System.exit(1)
        None
    }
  }
}
