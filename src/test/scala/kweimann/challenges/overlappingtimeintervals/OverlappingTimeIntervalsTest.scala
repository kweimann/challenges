package kweimann.challenges.overlappingtimeintervals

import org.scalatest.{FlatSpec, Matchers}

class OverlappingTimeIntervalsTest extends FlatSpec with Matchers {

  it should "find untimed intervals" in {
    val input = List(
      (2, 6),
      (9, 12),
      (8, 9),
      (18, 21),
      (4, 7),
      (10, 11))

    val output = List(
      (7, 8),
      (12, 18)
    )

    OverlappingTimeIntervals.findUntimedIntervals(input) shouldEqual output
  }
}