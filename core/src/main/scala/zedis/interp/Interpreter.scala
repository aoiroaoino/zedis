package zedis
package interp

import scalaz.{~>, Id, Applicative}
import scalaz.Id.Id

import zedis.adt.CommandADT

class Interpreter[F[_]: Applicative] {

  def impl: CommandADT ~> F = new (CommandADT ~> F) {
    def apply[A](adt: CommandADT[A]): F[A] = (proc[A] orElse unsupportedError)(adt)
  }

  def proc[A]: CommandADT[A] =>? F[A] =
    clusterProc[A]      orElse
    connectionProc[A]   orElse
    geoProc[A]          orElse
    hashesProc[A]       orElse
    hyperLogLogProc[A]  orElse
    keysProc[A]         orElse
    listsProc[A]        orElse
    pubSubProc[A]       orElse
    scriptingProc[A]    orElse
    serverProc[A]       orElse
    setsProc[A]         orElse
    sortedSetsProc[A]   orElse
    stringsProc[A]      orElse
    transactionsProc[A]

  def clusterProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def connectionProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def geoProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def hashesProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def hyperLogLogProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def keysProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def listsProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def pubSubProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def scriptingProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def serverProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def setsProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def sortedSetsProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def stringsProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def transactionsProc[A]: CommandADT[A] =>? F[A] = PartialFunction.empty

  def unsupportedError[A]: CommandADT[A] =>? F[A] = {
    case other => sys.error(s"""$other is unsupported command""")
  }

  protected def wrapF[A](v: => A): F[A] = Applicative[F].point(v)
}
