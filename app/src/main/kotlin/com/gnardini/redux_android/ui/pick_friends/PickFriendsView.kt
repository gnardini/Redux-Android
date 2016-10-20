package com.gnardini.redux_android.ui.pick_friends

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.widget.AppCompatCheckBox
import android.widget.CompoundButton
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.gnardini.redux_android.Store
import com.gnardini.redux_android.ui.pick_friends.PickFriendsReducer.PickFriendsAction
import com.gnardini.redux_android.ui.pick_friends.PickFriendsReducer.PickFriendsState
import rx.Subscription
import trikita.anvil.Anvil
import trikita.anvil.DSL.*
import trikita.anvil.RenderableAdapter
import trikita.anvil.appcompat.v7.AppCompatv7DSL
import trikita.anvil.recyclerview.v7.RecyclerViewv7DSL
import trikita.anvil.recyclerview.v7.RecyclerViewv7DSL.linearLayoutManager
import trikita.anvil.recyclerview.v7.RecyclerViewv7DSL.recyclerView

class PickFriendsView(
        val store: Store<PickFriendsState, PickFriendsAction>,
        context: Context) :
        FrameLayout(context) {

    var peopleAdapter = peopleAdapter()
    val stateSubscription: Subscription

    init {
        stateSubscription = store.observeState().subscribe { state -> stateUpdated() }
        populateView()
    }

    fun stateUpdated() {
        Anvil.render()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stateSubscription.unsubscribe()
    }

    fun refreshPeopleList() = peopleAdapter.notifyDataSetChanged()

    fun populateView() {
        Anvil.mount(this) {
            recyclerView {
                linearLayoutManager()
                RecyclerViewv7DSL.adapter(peopleAdapter)
            }
        }
    }

    fun peopleAdapter(): PersonAdapter =
            PersonAdapter(store, RenderableAdapter.Item { pos, item ->
                linearLayout {
                    size(MATCH, dip(80))
                    margin(dip(8))
                    orientation(LinearLayout.HORIZONTAL)

                    linearLayout {
                        size(0, WRAP)
                        weight(1f)
                        orientation(LinearLayout.VERTICAL)

                        textView {
                            text("Name: ${item.person.name}")
                            textColor(Color.BLACK)
                            margin(dip(8))
                        }

                        textView {
                            text("Contacts: ${item.person.contactsCount}")
                            textColor(Color.DKGRAY)
                            margin(dip(8))
                        }
                    }

                    AppCompatv7DSL.appCompatCheckBox {
                        layoutGravity(CENTER)
                        AppCompatv7DSL.supportButtonTintList(ColorStateList.valueOf(Color.GREEN))
                        onCheckedChange { button: CompoundButton?, selected ->
                            store.dispatchAction(PickFriendsAction.PersonChosen(item, selected))
                        }

                        // Using just checked(item.selected) doesn't work.
                        // Reported issue: https://github.com/zserge/anvil/issues/84
                        val checkbox = Anvil.currentView<AppCompatCheckBox>()
                        checkbox.isChecked = item.selected
                    }
                }
            })

}
