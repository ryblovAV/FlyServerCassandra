package utl.cassandra

import com.typesafe.config.ConfigFactory
import com.websudos.phantom.connectors.{ContactPoints, KeySpaceDef}
import com.websudos.phantom.dsl._

trait Connector {
  implicit def space: KeySpace
  implicit def session: Session
}

object Defaults {

  val config = ConfigFactory.load()

  val hosts = Seq(config.getString("cassandra.host"))
  val keySpace = config.getString("cassandra.keyspace")

  val connector = ContactPoints(hosts).keySpace(keySpace)
}

class FlyDatabase(val keyspace: KeySpaceDef) extends Database(keyspace) {
  object flyFiles extends ConcreteFlyFiles with keyspace.Connector
}

object FlyDatabase extends FlyDatabase(Defaults.connector)
