// http://latingrails.blogspot.com/2009/06/nl2br-in-grails-english-version.html

class NL2BRCodec {
    static encode = { string ->
        string.trim().replaceAll("\n","<br />")
    }
}
