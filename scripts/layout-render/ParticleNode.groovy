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

// ParticleNode wraps a UMLClassElement in a force-based algorithm

class ParticleNode {
    def velocity
    def umlClassElement
    def linksAndBackLinks

    def ParticleNode(element) {
        velocity = new Point(0, 0)
        umlClassElement = element

        linksAndBackLinks = []
        umlClassElement.links.each {
            linksAndBackLinks << it
        }
    }

    def setCenter(center) {
        umlClassElement.setCenter(center)
    }

    def getCenter() {
        umlClassElement.getCenter()
    }

    def addBackLink(link) {
        linksAndBackLinks << link
    }

    def links() {
        umlClassElement.links
    }

    def getAllLinks() {
        linksAndBackLinks
    }

    def connectorSegmentTo(other) {
        umlClassElement.connectorSegmentTo(other)
    }

    def lineSegments() {
        umlClassElement.lineSegments
    }

    def mass() {
        umlClassElement.width() * umlClassElement.height()
    }

    String toString() {
        umlClassElement.toString()
    }
}
