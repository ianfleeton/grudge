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

class ForceBasedAlgorithm {
    def totalKineticEnergy
    def nodes
    def random = new Random()
    def bestSolutionSegments

    def timestep = 0.11
    def damping = 0.99
    def negligibleKineticEnergy = 3.0

    def proportionalityConstant = 150000
    def springConstant = 0.17

    def snapToGridSize = 15

    // 0 > n >= 1
    // 1 = no effect
    // < 1 = pull towards centre of grid
    // used to prevent separated graphs from over-repelling
    def blackHoleFactor = 0.98

    def layout(diagramSpecification)
    {
        def startTime = System.currentTimeMillis()
        def timeCheck = 0

        nodes = []
        diagramSpecification.umlClassElements.each {
            nodes << new ParticleNode(it)
        }
        randomizeNodePositions()

        // discover back links
        nodes.each { thisNode ->
            nodes.each { otherNode ->
                if(otherNode != thisNode) {
                    // look to see if its links back are these
                    otherNode.links().each { incomingLink ->
                        if(incomingLink == thisNode.umlClassElement) {
                            thisNode.addBackLink(otherNode.umlClassElement)
                        }
                    }
                }        
            }
        }

        while(true) {
            totalKineticEnergy = 0.0

            nodes.each {
                def netForce = new Point(0.0, 0.0)
                nodes.each { otherNode ->
                    if(otherNode != it) {
                        netForce += coulombRepulsion(it, otherNode)
                    }
                }

                it.allLinks.each { link ->
                    if(link != it.umlClassElement) {
                        def spring = it.connectorSegmentTo(link)
                        netForce += hookeAttraction(it, spring)
                    }
                }

                it.velocity = ((netForce * timestep) + it.velocity) * damping
                it.setCenter((it.getCenter() + it.velocity * timestep) * blackHoleFactor)
                def speedSquared = it.velocity.relativeDistanceFrom(new Point(0.0,0.0))
                totalKineticEnergy += speedSquared * it.mass()
                timeCheck += 1
            }

            if(totalKineticEnergy < negligibleKineticEnergy) {
                // Quitting due to no energy
                break
            }
            if(timeCheck > 15000) {
                // Quitting due to time check
                break
            }
        }
        def endTime = System.currentTimeMillis()
        //println "Running time ${endTime-startTime}ms"
        snapNodesToGrid()
    }

    def snapNodesToGrid() {
        nodes.each {
            def c = it.getCenter()
            c.x = Math.round(c.x / snapToGridSize) * snapToGridSize
            c.y = Math.round(c.y / snapToGridSize) * snapToGridSize
            it.setCenter(c)
        }
    }

    def randomizeNodePositions() {
        def random = new Random()
        nodes.each {
            it.umlClassElement.bottomRight.x = it.umlClassElement.topLeft.x + 80
            it.umlClassElement.bottomRight.y = it.umlClassElement.topLeft.y + 40
            it.setCenter(new Point(-400.00 + 800.0 * random.nextDouble(), -400.00 + 1000.0 * random.nextDouble()))
        }
    }

    def coulombRepulsion(q1, q2) {
        def p1 = q1.getCenter()
        def p2 = q2.getCenter()
        def s = new LineSegment(p2, p1)
        def r2 = s.length() ** 2
        def r21 = s.unitVector()

        def repulsion = r21 * (proportionalityConstant * (1/r2))
        return repulsion
    }

    def hookeAttraction(particle, spring) {
        def x = spring.p1 - spring.p2
        def attraction = x * -springConstant
        return attraction
    }
}
