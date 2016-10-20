package com.gnardini.redux_android.ui.pick_friends

import com.gnardini.redux_android.Store
import com.gnardini.redux_android.repository.UsersRepository
import com.gnardini.redux_android.ui.pick_friends.PickFriendsReducer.PickFriendsAction
import com.gnardini.redux_android.ui.pick_friends.PickFriendsReducer.PickFriendsState
import rx.Observable
import rx.android.schedulers.AndroidSchedulers

object PickFriendsMiddleware {

    fun peopleFetcher(usersRepository: UsersRepository):
            (Store<PickFriendsState, PickFriendsAction>, PickFriendsAction) -> Unit = {
        store, action ->
        if (action is PickFriendsReducer.PickFriendsAction.FetchPeople) {
            usersRepository.fetchPeople()
                    .flatMap { people ->
                        Observable.from(listOf(
                                PickFriendsReducer.PickFriendsAction.PeopleLoaded(people),
                                PickFriendsReducer.PickFriendsAction.RefreshPeopleList))
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { action -> store.dispatchAction(action) }
                    .subscribe()
        }
    }

    fun peopleListRefresher(pickFriendsView: PickFriendsView):
            (Store<PickFriendsState, PickFriendsAction>, PickFriendsAction) -> Unit = {
        store, action ->
        if (action is PickFriendsAction.RefreshPeopleList) {
            pickFriendsView.refreshPeopleList()
        }
    }

}
