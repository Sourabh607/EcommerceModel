import common.{postgresCommon, sparkCommon}
import org.apache.spark.sql.SaveMode
import org.slf4j.LoggerFactory

object ecommTransMain {

  private val Logger = LoggerFactory.getLogger(getClass.getName)

  def main(args: Array[String]): Unit = {

    try {
      Logger.info("Main Method Started")
      Logger.warn("Logger Level set to Warn")

      val spark = sparkCommon.createSparkSession().get
      val pgDb = "Futurex"
      val pgTable = "dbo.futureorders"
      val futureOrdersDF = postgresCommon.fetchDataframeFromPgTable(spark, pgDb, pgTable).get
      futureOrdersDF.show(false)

      // Writing the Dataframe
      futureOrdersDF.write.mode(SaveMode.Overwrite).format("csv").save("HiveWarehouse")
      Logger.info("datafrae written successfully")

    }
    catch {
      case e: Exception =>
        Logger.error("Exception occured in main method " +e.printStackTrace())
    }
    Logger.info("Main Method Ended")
  }

}
