package com.jesusvilla.core.network.data

object Session {
    var session: String? = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiOWQwYmI5M2FhZTk2YTQ3MzZjYzc4NGVmNjIzN2Y3ZiIsIm5iZiI6MTcxOTYzOTIzMi4yMzUxODQsInN1YiI6IjYyMjg1M2NlMmY3OTE1MDA2NDVmNDQyYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zcf5c0pOLnSVGUHnXukoLKg_z_LiBw1bwx82QuRFJKw"
    var nextUrl: String = ""
    var authenticationListener: AuthenticationListener? = null


    fun invalidate() {
        // sending logged out event to it's listener
        // i.e: Activity, Fragment, Service
        session = null
        authenticationListener?.onUserLoggedOut()
    }

    fun hasSession() = !session.isNullOrEmpty()
}

interface AuthenticationListener {
    fun onUserLoggedOut()
}