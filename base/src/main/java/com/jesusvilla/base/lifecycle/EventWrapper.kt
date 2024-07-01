package com.jesusvilla.base.lifecycle

/**
 * Created by Jesús Villa on 6/08/23
 */
/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * The advantage of this approach is that the user needs to specify the intention by
 * using getContentIfNotHandled() or peekContent(). This method models the events as
 * part of the state: they’re now simply a message that has been consumed or not.
 *
 * {@link https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150}
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}