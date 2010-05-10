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

import java.awt.image.*
import javax.imageio.*
import javax.imageio.ImageIO
import java.awt.Graphics2D
import java.awt.Color
import java.awt.RenderingHints

class DiagramRenderer {
    int width
    int height
    Graphics2D g2d
    def lineSegments

    def scale = 1.0
    def maxWidth = 800.0
    def padding = 10

    def filename = "renderer-diagram.png"
    def bottomRight
    def diagramSpecification

    def render(spec) {
        diagramSpecification = spec
        moveToOrigin()
        determineSize()
        if(width > maxWidth) {
            scale = maxWidth / width
            scaleDiagram()
            determineSize()
        }
        println "Rendering diagram: size ${width}x${height} with scaling factor ${scale}"
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        g2d = image.createGraphics()

        whiteBackground()

        antialias()

        g2d.setColor(Color.BLACK)

        drawLineSegments()

        writeClassNames();

        g2d.dispose()
        ImageIO.write(image, "png", new File(filename))
    }

    def scaleDiagram() {
        diagramSpecification.umlClassElements.each {
            it.topLeft *= scale
            it.bottomRight *= scale
        }
    }

    def segmentsFrom(diagramSpecification) {
        def lineSegments = []
        diagramSpecification.umlClassElements.each {
            it.lineSegments.each {
                lineSegments << it
            }
            it.links.each { link ->
                def connector = it.connectorSegmentTo(link)
                lineSegments << connector
            }
        }
        return lineSegments
    }

    def moveToOrigin() {
        def topLeft = findTopLeftCorner()
        diagramSpecification.umlClassElements.each {
            it.bottomRight -= topLeft
            it.topLeft -= topLeft
        }
    }

    def determineSize() {
        lineSegments = segmentsFrom(diagramSpecification)
        bottomRight = findBottomRightCorner()
        width = (int)bottomRight.x + 2*padding
        height = (int)bottomRight.y + 2*padding
    }

    def whiteBackground() {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
    }

    def antialias() {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    }

    def drawLineSegments() {
        lineSegments.each {
            g2d.drawLine(
                (int)it.p1.x + padding, (int)it.p1.y + padding,
                (int)it.p2.x + padding, (int)it.p2.y + padding)
        }
    }

    def writeClassNames() {
        diagramSpecification.umlClassElements.each {
            def c = it.getCenter()
            g2d.drawString(it.name, (int)(c.x + padding - 30), (int)(c.y + padding + 5))
        }
    }

    def findBottomRightCorner() {
        def bottomRight = new Point(-100000, -100000)
        diagramSpecification.umlClassElements.each {
            if(it.topLeft.x > bottomRight.x) { bottomRight.x = it.topLeft.x }
            if(it.bottomRight.x > bottomRight.x) { bottomRight.x = it.bottomRight.x }
            if(it.topLeft.y > bottomRight.y) { bottomRight.y = it.topLeft.y }
            if(it.bottomRight.y > bottomRight.y) { bottomRight.y = it.bottomRight.y }
        }
        bottomRight
    }

    def findTopLeftCorner() {
        def topLeft = new Point(100000, 100000)
        diagramSpecification.umlClassElements.each {
            if(it.topLeft.x < topLeft.x) { topLeft.x = it.topLeft.x }
            if(it.bottomRight.x < topLeft.x) { topLeft.x = it.bottomRight.x }
            if(it.topLeft.y < topLeft.y) { topLeft.y = it.topLeft.y }
            if(it.bottomRight.y < topLeft.y) { topLeft.y = it.bottomRight.y }
        }
        topLeft
    }
}
