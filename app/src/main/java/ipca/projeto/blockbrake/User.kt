package ipca.projeto.blockbrake

class User {
    var name : String? = null
    var lastPoints : Long? = null
    var maxPoints : Long? = null


 //fun toHashMap() : HashMap<String, Comparable<Any>?> {
 //    val post = hashMapOf(
 //        "description" to description,
 //        "photo"       to photo,
 //        "date"        to date,
 //        "user"        to user
 //    )

 //    return post as HashMap<String, Comparable<Any>?>
 //}

    companion object {
        fun fromHashMap(hash: Map<String, Any>) : User {
            var user = User()

          user.name = hash["Name"] as String?
          user.lastPoints= hash["LastPoints"] as Long?
          user.maxPoints = hash["MaxPoints"] as Long?

            return user
        }
    }

}
