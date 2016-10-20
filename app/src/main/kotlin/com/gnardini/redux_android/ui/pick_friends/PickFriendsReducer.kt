package com.gnardini.redux_android.ui.pick_friends

import com.gnardini.redux_android.base.Action
import com.gnardini.redux_android.base.Reducer
import com.gnardini.redux_android.base.State
import com.gnardini.redux_android.model.Person
import com.gnardini.redux_android.ui.pick_friends.PickFriendsReducer.PickFriendsAction
import com.gnardini.redux_android.ui.pick_friends.PickFriendsReducer.PickFriendsAction.*
import com.gnardini.redux_android.ui.pick_friends.PickFriendsReducer.PickFriendsState

class PickFriendsReducer: Reducer<PickFriendsState, PickFriendsAction> {

    data class PersonState(val person: Person, val selected: Boolean)

    data class PickFriendsState(val contacts: List<PersonState>) : State

    sealed class PickFriendsAction() : Action {
        object FetchPeople : PickFriendsAction()
        object RefreshPeopleList: PickFriendsAction()
        class PeopleLoaded(val people: List<Person>) : PickFriendsAction()
        object PeopleLoadFailed : PickFriendsAction()
        class PersonChosen(val personState: PersonState, val selected: Boolean) : PickFriendsAction()
    }

    override fun reduce(state: PickFriendsState, action: PickFriendsAction): PickFriendsState {
        return when (action) {
            is FetchPeople -> state
            is RefreshPeopleList -> state
            is PeopleLoaded -> onPeopleLoaded(state, action.people)
            is PeopleLoadFailed -> onPeopleLoadFailed(state)
            is PersonChosen -> onPersonChosen(state, action.personState, action.selected)
        }
    }

    fun onPeopleLoaded(state: PickFriendsState, people: List<Person>): PickFriendsState {
        val contacts = people.map { person -> PersonState(person, selected = false) }
        return state.copy(contacts = contacts)
    }

    fun onPeopleLoadFailed(state: PickFriendsState): PickFriendsState = state

    fun onPersonChosen(state: PickFriendsState, chosenPersonState: PersonState, selected: Boolean):
            PickFriendsState {
        val contacts = state.contacts.map { personState ->
            if (personState.person == chosenPersonState.person) {
                PersonState(personState.person, selected)
            } else {
                personState
            }
        }
        return state.copy(contacts = contacts)
    }

}
