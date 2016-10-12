package com.gnardini.redux_android.pick_friends

import com.gnardini.redux_android.Store
import com.gnardini.redux_android.model.Person
import com.gnardini.redux_android.pick_friends.PickFriendsReducer.PickFriendsAction
import com.gnardini.redux_android.pick_friends.PickFriendsReducer.PickFriendsState
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun PickFriendsView.peopleFetcher() = { store: Store<PickFriendsState, PickFriendsAction>,
                                        action: PickFriendsAction ->
    if (action is PickFriendsAction.FetchPeople) {
        Observable.create<PickFriendsAction> { subscriber ->
            val people = mutableListOf<Person>()
            for (i in 1..30) {
                people.add(Person(name = "Person $i", contactsCount = i))
            }
            subscriber.onNext(PickFriendsAction.PeopleLoaded(people))
            subscriber.onNext(PickFriendsAction.RefreshPeopleList)
            subscriber.onCompleted()
        }.delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { action -> store.dispatchAction(action) }
                .subscribe()
    }
}

fun PickFriendsView.peopleListRefresher() = { store: Store<PickFriendsState, PickFriendsAction>,
                                              action: PickFriendsAction ->
    if (action is PickFriendsAction.RefreshPeopleList) {
        peopleAdapter.notifyDataSetChanged()
    }
}
