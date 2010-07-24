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

class SpecificationParser {
    def elementsMap = [:]
    def diagramSpecification

    def parse(textSpec) {
        diagramSpecification = new DiagramSpecification()
        textSpec.eachLine {
            if(it.contains(']-[')) {
                parseAssociation(it);
            } else if(it.contains('{')) {
                parseDeclarationWithLayout(it);
            } else {
                parseDeclaration(it);
            }
        }
        elementsMap.each {
            diagramSpecification.umlClassElements << it.value
        }
    }

    def parseAssociation(line) {
        def matcher = line =~ /\w+/
        elementsMap[matcher[0]].links << elementsMap[matcher[1]]
    }

    def parseDeclaration(line) {
        def matcher = line =~ /\w+/
        def element = new UMLClassElement()
        element.name = matcher[0]
        elementsMap[matcher[0]] = element
    }

    def parseDeclarationWithLayout(line) {
        // Floating point regex from http://www.regular-expressions.info/floatingpoint.html
        def matcher = line =~ /\[(\w+)\{rect:([-+]?[0-9]*\.?[0-9]+),([-+]?[0-9]*\.?[0-9]+),([-+]?[0-9]*\.?[0-9]+),([-+]?[0-9]*\.?[0-9]+)\}\]/
        def element = new UMLClassElement()
        element.name = matcher[0][1]
        elementsMap[matcher[0][1]] = element
        element.topLeft.x = matcher[0][2].toFloat()
        element.topLeft.y = matcher[0][3].toFloat()
        element.bottomRight.x = matcher[0][4].toFloat()
        element.bottomRight.y = matcher[0][5].toFloat()
    }
}