package com.example.calculator

class HotKey<T>(vararg keys: T, private val callback: () -> Unit) {
    private val keysList = listOf(*keys)
    private var neededKeyIndex = 0

    fun match(key: T) : Boolean {
        if(keysList[neededKeyIndex++] == key) {
            if(neededKeyIndex >= keysList.size) {
                neededKeyIndex = 0
                callback.invoke()
                return true
            }
        }
        else {
            neededKeyIndex = 0
        }
        return false
    }
}

class HotKeysManager() {
    private val hotKeys = mutableListOf<HotKey<Int>>()

    fun addHotKey(hotKey: HotKey<Int>) = hotKeys.add(hotKey)
    fun removeHotKey(hotKey: HotKey<Int>) = hotKeys.remove(hotKey)

    fun addClickedButton(button: Int) {
        for (hotKey in hotKeys)
            hotKey.match(button)
    }
}