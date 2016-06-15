package zedis
package command

trait AllCommand
  extends ConnectionCommand
  with    HasheCommand
  with    KeyCommand
