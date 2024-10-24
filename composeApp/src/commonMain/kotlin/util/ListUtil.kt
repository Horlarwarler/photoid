package util


fun <T> List<T>.addToList(item: T): List<T> {
    val mutableList = toMutableList()
    mutableList.add(element = item)
    return mutableList.toList()
}