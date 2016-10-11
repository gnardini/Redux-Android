package com.gnardini.redux_android.pick_friends

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.widget.CompoundButton
import android.widget.LinearLayout
import com.gnardini.redux_android.Store
import com.gnardini.redux_android.pick_friends.PickFriendsStateManager.*
import trikita.anvil.Anvil
import trikita.anvil.DSL.*
import trikita.anvil.RenderableAdapter
import trikita.anvil.RenderableRecyclerViewAdapter
import trikita.anvil.appcompat.v7.AppCompatv7DSL
import trikita.anvil.recyclerview.v7.RecyclerViewv7DSL
import trikita.anvil.recyclerview.v7.RecyclerViewv7DSL.linearLayoutManager
import trikita.anvil.recyclerview.v7.RecyclerViewv7DSL.recyclerView

class PickFriendsActivity : AppCompatActivity() {

    val store: Store<PickFriendsState, PickFriendsAction, PickFriendsCommand> =
            Store(PickFriendsStateManager())

    var peopleAdapter = peopleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store.subscribe { stateUpdated() }
        populateView()
        store.dispatchAction(PickFriendsAction.FetchPeople)
    }

    fun stateUpdated() {
        Anvil.render()
    }

    fun populateView() {
        Anvil.mount(findViewById(android.R.id.content)) {
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

                        // Just checked(item.selected) doesn't work.
                        // Reported issue: https://github.com/zserge/anvil/issues/84
                        val checkbox = Anvil.currentView<AppCompatCheckBox>()
                        checkbox.isChecked = item.selected
                    }
                }
            })

    class PersonAdapter(val store: Store<PickFriendsState, PickFriendsAction, PickFriendsCommand>,
                        val renderer: RenderableAdapter.Item<PickFriendsStateManager.PersonState>)
            : RenderableRecyclerViewAdapter() {

        private fun items() = store.getState().contacts

        override fun getItemCount() = items().size

        override fun view(holder: RecyclerView.ViewHolder) {
            val position = holder.layoutPosition
            renderer.view(position, items()[position])
        }
    }

}
