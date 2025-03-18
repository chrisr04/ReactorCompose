package com.animotionsoftware.lib.reactor.viewmodel

internal class ReactorViewModelMock<Event : ReactorEvent, State>(
    mockedState: State,
) : ReactorViewModel<Event, State>(mockedState)