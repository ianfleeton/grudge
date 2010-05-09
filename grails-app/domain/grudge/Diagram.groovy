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

import java.security.MessageDigest

class Diagram {
    String title
    String logicalSpecificationHash
    String logicalSpecification
    String layoutSpecification

    def hash() {
        MessageDigest digest = MessageDigest.getInstance('MD5')
        byte[] input = logicalSpecification.getBytes()
        digest.update(input)
        BigInteger b = new BigInteger(1, digest.digest())
        return b.toString(16).padLeft(32, '0')
    }

    static constraints = {
        title(blank:false, maxSize:80)
        logicalSpecificationHash(length:32)
        logicalSpecification(maxSize:32000)
        layoutSpecification(maxSize:40000)
    }
}
