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

package grudge

class LineSegmentTests extends GroovyTestCase {
    def ls1, ls2, ls3, ls4, ls5

    void setUp() {
        ls1 = new LineSegment(
            new Point(0.0, 0.0),
            new Point(10.0, 10.0)
        )
        ls2 = new LineSegment(
            new Point(0.0, 10.0),
            new Point(10.0, 0.0)
        )
        ls3 = new LineSegment(
            new Point(1.0, 10.0),
            new Point(11.0, 0.0)
        )
        ls4 = new LineSegment(
            new Point(0.0, 10.0),
            new Point(10.0, 10.0)
        )

        // has triangle sides of length 3, 4 and 5
        ls5 = new LineSegment(
            new Point(-1.0, 1.0),
            new Point(2.0, -3.0)
        )
    }

    void testLineSegmentIntersections() {
        assertInspect ls1.pointOfIntersectionWith(ls2), "(5.0, 5.0)"
        assertEquals "ls2 and ls3 do not intersect (parallel)", 0, ls2.pointOfIntersectionWith(ls3)
        // ls1 and ls4 intersect where end points meet at (10.0, 10.0)
        assertInspect ls1.pointOfIntersectionWith(ls4), "(10.0, 10.0)"
    }

    void testLength() {
        // 4^2 + 3^2 = 25
        // 25^0.5 = 5
        assertEquals "ls5's length is 5.0", 5.0, ls5.length()
    }

    void testUnitVector() {
        // 0.6 = 3/5
        // -0.8 = -4/5
        assertInspect ls5.unitVector(), "(0.6, -0.8)"
    }

    void testToString() {
        assertEquals "ls5.toString", "(-1.0, 1.0) -> (2.0, -3.0)", ls5.toString()
    }
}
