import simulacrum._

@typeclass trait Functor[F[_]] {self =>
  def map[A, B](fa: F[A])(f: A => B): F[B]

  def lift[A, B](f: A => B): F[A] => F[B] =
    fa => map(fa)(f)

  def as[A, B](fa: F[A], b: => B): F[B] =
    map(fa)(_ => b)

  def void[A](fa: F[A]): F[Unit] =
    as(fa, ())

  def compose[G[_]](implicit G: Functor[G]): Functor[Lambda[X => F[G[X]]]] =
    new Functor[Lambda[X => F[G[X]]]] {
      def map[A, B](fag: F[G[A]])(f: A => B): F[G[B]] = {
        self.map(fag)(ga => G.map(ga)(a => f(a)))
      }
    }
}

trait FunctorLaws {
  def indentity[F[_], A](fa: F[A])(implicit F: Functor[F]) = F.map(fa)(a => a) == fa

  def composition[F[_], A, B, C](fa: F[A], f: A => B, g: B => C)(implicit F: Functor[F]) =
    F.map(F.map(fa)(f))(g) == F.map(fa)(f andThen g)
}

object Functor {
  implicit val listFunctor: Functor[List] = new Functor[List] {
    def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }

  implicit val optionFunctor: Functor[Option] = new Functor[Option] {
    def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }

  implicit def function1Functor[X]: Functor[X => ?] = new Functor[X => ?] {
    def map[A, B](fa: X => A)(f: A => B): X => B = fa andThen f
  }
}

object MainTest extends App {
  val res = implicitly[Functor[List]]
  println(res.map(List(1,2,3))(_ + 1))

  val res1 = implicitly[Functor[Option]]
  println(res1.map(Some(1))(_ + 1))

  val res2 = implicitly[Functor[Int => ?]]
  val res3 = res2.map(_ + 1)(_ + 2)
  println(res3(5))

  val res4 = Functor[List] compose Functor[Option]
  val xs: List[Option[Int]] = List(Some(1), None, Some(2))
  //println(xs.map(_ + 1))
  println(res4.map(xs)(_ + 1))






}