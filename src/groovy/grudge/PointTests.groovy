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

class PointTests extends GroovyTestCase {
    def p1, p2

    void setUp() {
        p1 = new Point(2.0, 2.0)
        p2 = new Point(5.0, 6.0)
    }

    void testToString() {
        assertEquals "p2 toString", "(5.0, 6.0)", p2.toString()
    }

    void testDistanceFrom() {
        def d = p2.distanceFrom(p1)
        assertEquals "d == 5", 5, d
    }

    void testRelativeDistanceFrom() {
        def d = p2.relativeDistanceFrom(p1)
        assertEquals "d == 25", 25, d
    }

    void testAddition() {
        def p3 = p1 + p2
        assertEquals "p3.x == 7.0", 7.0, p3.x
        assertEquals "p3.y == 8.0", 8.0, p3.y
    }

    void testSubtraction() {
        def p3 = p2 - p1
        assertEquals "p3.x == 3.0", 3.0, p3.x
        assertEquals "p3.y == 4.0", 4.0, p3.y
    }

    void testMultiplication() {
        def p3 = p1 * 5.0
        assertEquals "p3.x == 10.0", 10.0, p3.x
        assertEquals "p3.y == 10.0", 10.0, p3.y
    }

    void testClone() {
        def p3 = p1.clone()
        assertFalse p1 == p3
        assertEquals "p1.x == p3.x", p1.x, p3.x
        assertEquals "p1.y == p3.y", p1.y, p3.y
    }
}