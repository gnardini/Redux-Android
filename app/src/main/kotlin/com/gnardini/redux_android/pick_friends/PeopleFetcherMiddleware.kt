package com.gnardini.redux_android.pick_friends

import com.gnardini.redux_android.Store
import com.gnardini.redux_android.pick_friends.PickFriendsReducer.PickFriendsAction
import com.gnardini.redux_android.pick_friends.PickFriendsReducer.PickFriendsState
import com.gnardini.redux_android.repository.UsersRepository
import rx.Observable
import rx.android.schedulers.AndroidSchedulers

fun PickFriendsView.peopleFetcher(usersRepository: UsersRepository) =
        { store: Store<PickFriendsState, PickFriendsAction>,
          action: PickFriendsAction ->
            if (action is PickFriendsAction.FetchPeople) {
                usersRepository.fetchPeople()
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap { people ->
                            Observable.from(listOf(
                                    PickFriendsAction.PeopleLoaded(people),
                                    PickFriendsAction.RefreshPeopleList))
                        }
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
