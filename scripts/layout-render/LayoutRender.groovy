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

parser = new SpecificationParser()
parser.parse(logicalSpecification)
diagramSpec = parser.diagramSpecification

println "Running force-based algorithm"

algorithm = new ForceBasedAlgorithm()
algorithm.layout(diagramSpec)

renderer = new DiagramRenderer()
if(args.length) {
  renderer.filename = args[0]
}
renderer.render(diagramSpec)
