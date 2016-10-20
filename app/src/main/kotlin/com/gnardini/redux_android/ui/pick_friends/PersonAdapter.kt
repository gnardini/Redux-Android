package com.gnardini.redux_android.ui.pick_friends

import android.support.v7.widget.RecyclerView
import com.gnardini.redux_android.Store
import trikita.anvil.RenderableAdapter
import trikita.anvil.RenderableRecyclerViewAdapter

class PersonAdapter(
        val store: Store<PickFriendsReducer.PickFriendsState, PickFriendsReducer.PickFriendsAction>,
        val renderer: RenderableAdapter.Item<PickFriendsReducer.PersonState>) :
        RenderableRecyclerViewAdapter() {

    private fun items() = store.getState().contacts

    override fun getItemCount() = items().size

    override fun view(holder: RecyclerView.ViewHolder) {
        val position = holder.layoutPosition
        renderer.view(position, items()[position])
    }
}
