package data

import domain.PhotoIdPreference

class PhotoIdPrefImpl(
) : PhotoIdPreference {

    private val settings = createSettings()
    override fun getUUID(): String? {
        return settings.getStringOrNull(PhotoIdPreference.Constant.UUID_KEY)
    }

    override fun setUUID(uuid: String) {
        settings.putString(PhotoIdPreference.Constant.UUID_KEY, uuid)
    }


}