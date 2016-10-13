package com.gnardini.redux_android.repository

import com.gnardini.redux_android.model.Person
import rx.Observable
import rx.Single
import java.util.concurrent.TimeUnit

class UsersRepository {

    // Mock a 1 second delay for the API call and return success.
    fun login(email: String, password: String): Single<Boolean> {
        return Single.create<Boolean> { subscriber ->
            subscriber.onSuccess(true)
        }.delay(1, TimeUnit.SECONDS)
    }

    // Mock a 1 second delay for the API call and return the list of people.
    fun fetchPeople(): Observable<List<Person>> {
        return Observable.create<List<Person>> { subscriber ->
            val people = mutableListOf<Person>()
            for (i in 1..30) {
                people.add(Person(name = "Person $i", contactsCount = i))
            }
            subscriber.onNext(people)
            subscriber.onCompleted()
        }.delay(1, TimeUnit.SECONDS)
    }

}