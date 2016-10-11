package com.gnardini.redux_android.pick_friends

import android.util.Log
import com.gnardini.redux_android.base.Action
import com.gnardini.redux_android.base.Command
import com.gnardini.redux_android.base.State
import com.gnardini.redux_android.base.StateManager
import com.gnardini.redux_android.model.Person
import com.gnardini.redux_android.pick_friends.PickFriendsStateManager.*
import com.gnardini.redux_android.pick_friends.PickFriendsStateManager.PickFriendsAction.*
import com.gnardini.redux_android.pick_friends.PickFriendsStateManager.PickFriendsCommand.DoAction
import com.gnardini.redux_android.pick_friends.PickFriendsStateManager.PickFriendsCommand.None
import rx.Observable
import java.util.*

class PickFriendsStateManager :
        StateManager<PickFriendsState, PickFriendsAction, PickFriendsCommand> {

    data class PersonState(val person: Person, val selected: Boolean)

    data class PickFriendsState(val contacts: List<PersonState>) : State

    sealed class PickFriendsAction() : Action {
        object FetchPeople : PickFriendsAction()
        class PeopleLoaded(val people: List<Person>) : PickFriendsAction()
        object PeopleLoadFailed : PickFriendsAction()
        class PersonChosen(val personState: PersonState, val selected: Boolean): PickFriendsAction()
    }

    sealed class PickFriendsCommand() : Command {
        object None : PickFriendsCommand()
        class DoAction(val action: PickFriendsAction) : PickFriendsCommand()
    }

    override fun initialState(): PickFriendsState = PickFriendsState(LinkedList())

    override fun update(state: PickFriendsState, action: PickFriendsAction):
            Pair<PickFriendsState, PickFriendsCommand> {
        return when (action) {
            is FetchPeople -> Pair(state, DoAction(FetchPeople))
            is PeopleLoaded -> onPeopleLoaded(state, action.people)
            is PeopleLoadFailed -> onPeopleLoadFailed(state)
            is PersonChosen -> onPersonChosen(state, action.personState, action.selected)
        }
    }

    override fun applyCommand(state: PickFriendsState, command: PickFriendsCommand)
            : Observable<PickFriendsAction> {
        return when (command) {
            is None -> Observable.empty()
            is DoAction -> when (command.action) {
                is FetchPeople -> fetchPeople()
                else -> Observable.empty()
            }
        }
    }

    // Action handlers

    fun onPeopleLoaded(state: PickFriendsState, people: List<Person>)
            : Pair<PickFriendsState, PickFriendsCommand> {
        val contacts  = people.map { person -> PersonState(person, selected = false) }
        return Pair(state.copy(contacts = contacts), None)
    }

    fun onPeopleLoadFailed(state: PickFriendsState): Pair<PickFriendsState, PickFriendsCommand> {
        return Pair(state, None)
    }

    fun onPersonChosen(state: PickFriendsState, personState: PersonState, selected: Boolean)
            : Pair<PickFriendsState, PickFriendsCommand> {
        val contacts = state.contacts.map { contactState ->
            if (contactState.person == personState.person) {
                PersonState(contactState.person, selected)
            } else {
                contactState
            }
        }
        return Pair(state.copy(contacts = contacts), None)
    }

    // Command handlers

    fun fetchPeople(): Observable<PickFriendsAction> {
        return Observable.create { subscriber ->
            val people = mutableListOf<Person>()
            for (i in 1..30) {
                people.add(Person(name = "Person $i", contactsCount = i))
            }
            subscriber.onNext(PeopleLoaded(people))
            subscriber.onCompleted()
        }
    }

}
