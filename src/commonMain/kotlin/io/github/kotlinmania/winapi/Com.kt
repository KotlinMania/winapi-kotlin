package io.github.kotlinmania.winapi

import io.github.kotlinmania.winapi.shared.guiddef.GUID

/** This trait should be implemented for all COM interfaces. */
interface Interface {
    /** Returns the IID of the Interface. */
    fun uuidof(): GUID
}

/** This trait should be implemented for all COM classes. */
interface Class {
    /** Returns the CLSID of the Class. */
    fun uuidof(): GUID
}
