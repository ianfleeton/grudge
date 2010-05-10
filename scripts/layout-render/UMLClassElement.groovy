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

class UMLClassElement implements Cloneable {
    def topLeft
    def bottomRight

    def links = []
  
    def name = 'Class'

    def UMLClassElement() {
        topLeft = new Point(0.0, 0.0)
        bottomRight = new Point(0.0, 0.0)
    }

    def width()  { bottomRight.x - topLeft.x }
    def height() { bottomRight.y - topLeft.y }

    def getCenter() {
        new Point(
            (bottomRight.x + topLeft.x) / 2,
            (topLeft.y + bottomRight.y) / 2)
    }

    def setCenter(center) {
        def w = width()
        def h = height()
        topLeft.x = center.x - w/2;
        topLeft.y = center.y - h/2;
        bottomRight.x = center.x + w/2;
        bottomRight.y = center.y + h/2;
    }

    def nearestConnectorPointTo(otherPoint) {
        def nearest = center

        def testSegment = new LineSegment(center, otherPoint)

        lineSegments.each {
            def intersect = testSegment.pointOfIntersectionWith(it)
            if(intersect != 0) {
                nearest = intersect
            }
        }
        return nearest
    }

    def connectorSegmentTo(otherElement) {
        def otherConnectorPoint = otherElement.nearestConnectorPointTo(center)
        def thisConnectorPoint = nearestConnectorPointTo(otherElement.center)
        new LineSegment(thisConnectorPoint, otherConnectorPoint)
    }

    def getTopSegment() {
        new LineSegment(
            new Point(topLeft.x, topLeft.y),
            new Point(bottomRight.x, topLeft.y))
    }

    def getRightSegment() {
        new LineSegment(
            new Point(bottomRight.x, topLeft.y),
            new Point(bottomRight.x, bottomRight.y))
    }

    def getBottomSegment() {
        new LineSegment(
            new Point(bottomRight.x, bottomRight.y),
            new Point(topLeft.x, bottomRight.y))
    }

    def getLeftSegment() {
        new LineSegment(
            new Point(topLeft.x, bottomRight.y),
            new Point(topLeft.x, topLeft.y))
    }

    def getLineSegments() {
        [ topSegment, rightSegment, bottomSegment, leftSegment ]
    }

    String toString() {
        "${name} @ ${getCenter()} (${links.size()} links)"
    }
  
    def clone() {
        def o = new UMLClassElement()
        o.topLeft = topLeft.clone()
        o.bottomRight = bottomRight.clone()
        o.links = links.clone()
        o.name = name
        return o
    }
}
