## Redux Android

Despite the repo's name, this is not a strict Redux implementation. It's an implementation of a design pattern that uses a unidirectional data flow heavily inspired by Elm and Redux ideas.

- [Redux](http://redux.js.org/)
- [Elm architecture](https://guide.elm-lang.org/architecture/)

It's written on Kotlin, and uses Anvil for the UI.

### The Gist
[The Gist on Redux](http://redux.js.org/#the-gist)

The state of each view is stored on a [Store](https://github.com/gnardini/Redux-Android/blob/master/app/src/main/kotlin/com/gnardini/redux_android/Store.kt).

The state is immutable, and the only way to change it is to emit an Action. The action is sent to a [Reducer](https://github.com/gnardini/Redux-Android/blob/master/app/src/main/kotlin/com/gnardini/redux_android/base/Reducer.kt) along with the state and a new state is returned.

The View, which is just a regular Android view that extends from `View`, listens to updates on the state and must be able to be updated using only its contents.

### Middleware
[Middleware on Redux](http://redux.js.org/docs/advanced/Middleware.html)

> [Middleware] provides a third-party extension point between dispatching an action, and the moment it reaches the reducer

Middleware can be used to perform actions such as async tasks or routing, which need to act based on the state and action and don't want to change the state, but rather fire new actions or just listen to (state, action) pairs (for logging, for example).

### Navigation

To keep navigation simple, there's only one Activity, called `BaseActivity`. It contains the view currently being showed.

In charge of managing this, and pushing new views to be shown is the [Router](https://github.com/gnardini/Redux-Android/blob/master/app/src/main/kotlin/com/gnardini/redux_android/routing/Router.kt). It gets help from the [ViewFactory](https://github.com/gnardini/Redux-Android/blob/master/app/src/main/kotlin/com/gnardini/redux_android/routing/ViewFactory.kt) to create the actual views with their dependencies and listen to navigation actions using middleware.

## TODO
- Add tests.
- Add state time travel.
- Add enhanced crash reporting.
- Add a dispatcher to prevent actions being emitted before the last one is completely processed.
