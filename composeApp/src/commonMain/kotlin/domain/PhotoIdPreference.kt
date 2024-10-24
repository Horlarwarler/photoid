package domain

interface PhotoIdPreference {


    fun getUUID(): String?
    fun setUUID(uuid: String)

    object Constant {
        const val UUID_KEY = "UUID_KEY"
    }
}

