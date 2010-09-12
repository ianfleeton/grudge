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

class Point implements Cloneable {
    def x
    def y

    def Point(xx, yy) {
        x = xx
        y = yy
    }

    String toString() {
        "(${x}, ${y})"
    }

    def distanceFrom(other) {
        Math.sqrt(relativeDistanceFrom(other)) // hypotenuse
    }

    def relativeDistanceFrom(other) {
        (other.x-x)**2 + (other.y-y)**2 // hypotenuse squared
    }

    def plus(other) {
        new Point(x + other.x, y + other.y)
    }

    def minus(other) {
        new Point(x - other.x, y - other.y)
    }

    def multiply(scalar) {
        new Point(x * scalar, y * scalar)
    }

    def clone() {
        new Point(x, y)
    }
}
