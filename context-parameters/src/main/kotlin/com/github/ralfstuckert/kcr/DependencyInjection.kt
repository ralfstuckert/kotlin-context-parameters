package com.github.ralfstuckert.kcr

import java.net.URI

typealias GuestId = String

data class Guest(val id:GuestId, val firstName: String, val lastName: String)

interface GuestImageUriProvider {
    fun getGuestImageUri(guestId: GuestId): URI
}

/*
context(guestImageUriProvider: GuestImageUriProvider)
fun Guest.getImageUri() =
    guestImageUriProvider.getGuestImageUri(id)
*/

context(guestImageUriProvider: GuestImageUriProvider)
val Guest.imageUri: URI
    get() = guestImageUriProvider.getGuestImageUri(id)



class GuestService(val guestImageUriProvider: GuestImageUriProvider) {

    fun showGuestImage(guest: Guest)  = with(guestImageUriProvider) {
        println("Guest ${guest.lastName} with image URI: ${guest.imageUri}")
    }

}
