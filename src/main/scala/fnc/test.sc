
val res = implicitly[Functor[List]]

res.map(List(1,2,3))(_ + 1)
