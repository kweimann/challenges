package kweimann.challenges.overlappingtimeintervals

import scala.collection.mutable

object OverlappingTimeIntervals {
  import OverlappingTimeIntervals.Types._

  object Types {
    type Interval = (Int, Int)
  }

  def findUntimedIntervals(timeIntervals: List[Interval]): List[Interval] = {
    // ordering by interval end
    implicit val intervalOrdering: Ordering[Interval] = (a: Interval, b: Interval) => Ordering.Int.compare(a._2, b._2)

    // needs at least 2 intervals to find any untimed intervals
    if (timeIntervals.isEmpty || timeIntervals.tail.isEmpty) return List.empty

    // the algorithm will iterate over an ordered list of time intervals
    val heap = mutable.PriorityQueue(timeIntervals: _*)

    // minStart is the smallest start of an interval seen so far
    var (minStart, _) = heap.dequeue()
    var untimedIntervals = List.empty[Interval]

    // dequeue all elements starting from the last interval ordered by interval end
    while (heap.nonEmpty) {
      val (start, end) = heap.dequeue()
      // if next interval's end (note descending order) is smaller than the smallest start seen so far
      // the space between these two points creates an untimed interval
      if (end < minStart) untimedIntervals = (end, minStart) :: untimedIntervals
      // update
      minStart = Math.min(start, minStart)
    }

    untimedIntervals
  }
}
