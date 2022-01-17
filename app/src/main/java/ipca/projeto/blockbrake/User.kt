package ipca.projeto.blockbrake

class User {
    var name : String? = null
    var lastPoints : Long? = null
    var maxPoints : Long? = null
   // var email:String?=null

    companion object {
        fun fromHashMap(hash: Map<String, Any>) : User {
            var user = User()

          user.name = hash["Name"] as String?
          user.lastPoints= hash["LastPoints"] as Long?
          user.maxPoints = hash["MaxPoints"] as Long?
            //user.email=hash["Email"] as String?

            return user
        }
    }

}
