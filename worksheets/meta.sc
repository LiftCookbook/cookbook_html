import net.liftweb.util.Helpers._

val in = <meta name="keywords" content="words, here, please" />







val replace = "@keywords [content]" #> "words, we, really, want"

replace(in)


val remove = "@keywords [content!]" #> "words, here, please"

remove(in)

val add = "@keywords [content+]" #> ", thank you"

add(in)


