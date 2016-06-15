package utl.cassandra

import com.websudos.phantom.connectors.{ContactPoints, KeySpaceDef}
import com.websudos.phantom.dsl._
import config.CassandraConfig._

trait Connector {
  implicit def space: KeySpace
  implicit def session: Session
}

object Defaults {
  val hosts = Seq(host)
  val connector = ContactPoints(hosts).keySpace(keySpace)
}

class FlyDatabase(val keyspace: KeySpaceDef) extends Database(keyspace) {
  object flyFiles extends ConcreteFlyFiles with keyspace.Connector
}

object FlyDatabase extends FlyDatabase(Defaults.connector)
