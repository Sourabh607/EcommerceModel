package common

import java.util.Properties
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.slf4j.LoggerFactory

object postgresCommon {

  private val Logger = LoggerFactory.getLogger(getClass.getName)

  def getPostgresCommonProps() : Option[Properties] = {
   // Fetch Postgres Properties

    Logger.info("Fetching PG Connection Properties from getPostgresCommonProps() method")
    try {
      val pgConnectionProperties = new Properties()

      pgConnectionProperties.setProperty("Driver", "org.postgresql.Driver")
      pgConnectionProperties.setProperty("user", "postgres")
      pgConnectionProperties.setProperty("password", "postgres")

      Logger.info("Pg Connection Properties Fetched Successfully")
      Some(pgConnectionProperties)
    }
    catch {
      case e: Exception =>
        Logger.error("Exception occurred in getPostgresCommonProps method : " +e.printStackTrace())
        System.exit(1)
        None
    }

  }

  def getPgServerDatabaseURL(pgDb: String ) : String = {
  // Create JDBC URL for Postgres Connectivity

    Logger.info("JDBC URL : 'jdbc:postgresql://localhost:5432/Futurex'")
    val url = "jdbc:postgresql://localhost:5432/" + pgDb
    url
  }

  def fetchDataframeFromPgTable(spark : SparkSession, pgDb: String, pgTable: String) : Option[DataFrame] = {
   // Fetch PG Table as Dataframe

    Logger.info("Fetching Dataframe from PG Table : fetchDataframeFromPgTable() ")
    try {
      val pgTableDf = spark.read.jdbc(getPgServerDatabaseURL(pgDb), pgTable, getPostgresCommonProps().get)
      Some(pgTableDf)
    }
    catch {
      case e: Exception =>
        Logger.error("Exception Occurred in fetchDataframeFromPgTable() Method : " +e.printStackTrace())
        System.exit(1)
        None
    }

  }
}
