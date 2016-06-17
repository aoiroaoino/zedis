package zedis
package command

object all extends AllCommand

trait AllCommand
  extends ConnectionCommand
  with    HasheCommand
  with    KeyCommand
  with    ListCommand
  with    SetCommand
  with    StringCommand
