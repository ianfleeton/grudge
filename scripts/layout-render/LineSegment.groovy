/*
* Copyright 2010 Ian Fleeton
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

class LineSegment {
    def p1
    def p2

    def LineSegment(startPoint, endPoint) {
        p1 = startPoint
        p2 = endPoint
    }

    def length() {
        p1.distanceFrom(p2)
    }

    def unitVector() {
        def l = length()
        new Point(
            (p2.x - p1.x)/l,
            (p2.y - p1.y)/l
            )
    }

    String toString() {
        "${p1} -> ${p2}"
    }

    // code below, until --end-- is based on logic and code samples from
    // http://www.topcoder.com/tc?module=Static&d1=tutorials&d2=geometry2
    // accessed 14th February 2010

    // a*x+b*y=c

    def a() {
        p2.y - p1.y
    }

    def b() {
        p1.x - p2.x
    }

    def c() {
        a() * p1.x + b() * p1.y
    }

    def pointOfIntersectionWith(otherLineSegment) {
        def intersectionPoint = pointOfLineIntersectionWith(otherLineSegment)
        if(intersectionPoint) {
            if(pointIsInSegmentArea(intersectionPoint) &&
            otherLineSegment.pointIsInSegmentArea(intersectionPoint)) {
                return intersectionPoint
            }
        }
        return 0
    }

    def pointOfLineIntersectionWith(otherLine) {
        double det = a() * otherLine.b() - otherLine.a() * b()
        if(det == 0) {
            // lines are parallel -- not an intersection
            return 0
        }
        else {
            return new Point(
                (otherLine.b() * c() - b() * otherLine.c()) / det,
                (a() * otherLine.c() - otherLine.a() * c()) / det
                )
        }
    }

    def pointIsInSegmentArea(testPoint) {
        def tolerance = 0.01 // for horizontal and vertical lines
        Math.min(p1.x, p2.x) <= (testPoint.x + tolerance) &&
        (testPoint.x - tolerance) <= Math.max(p1.x, p2.x) &&
        Math.min(p1.y, p2.y) <= (testPoint.y + tolerance) &&
        (testPoint.y - tolerance) <= Math.max(p1.y, p2.y)
    }
    // -- end --
}
