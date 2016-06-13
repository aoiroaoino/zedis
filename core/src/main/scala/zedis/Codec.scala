package zedis

trait Codec[A] {

  def encode(a: A): String

  def decode(s: String): A
}

object Codec {

  def apply[A: Codec]: Codec[A] = implicitly[Codec[A]]

  implicit lazy val anyCodec: Codec[Any] = new Codec[Any] {
    override def encode(a: Any): String = a.toString
    override def decode(s: String): Any = s
  }

  implicit lazy val intCodec: Codec[Int] = new Codec[Int] {
    override def encode(a: Int): String = a.toString
    override def decode(s: String): Int = s.toInt
  }

  implicit lazy val longCodec: Codec[Long] = new Codec[Long] {
    override def encode(a: Long): String = a.toString
    override def decode(s: String): Long = s.toLong
  }

  implicit lazy val floatCodec: Codec[Float] = new Codec[Float] {
    override def encode(a: Float): String = a.toString
    override def decode(s: String): Float = s.toFloat
  }

  implicit lazy val doubleCodec: Codec[Double] = new Codec[Double] {
    override def encode(a: Double): String = a.toString
    override def decode(s: String): Double = s.toDouble
  }

  implicit lazy val charCodec: Codec[Char] = new Codec[Char] {
    override def encode(a: Char): String = a.toString
    override def decode(s: String): Char = s.charAt(0)
  }

  implicit lazy val stringCodec: Codec[String] = new Codec[String] {
    override def encode(a: String): String = a
    override def decode(s: String): String = s
  }
}
