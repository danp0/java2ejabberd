-module(factorial).

-export(
   [
    f/1,
    main/1
   ]
  ).

f(0) ->
  1;
f(N) when N > 0 ->
  N * f(N-1).

main([Arg]) when is_atom(Arg) ->
  N = list_to_integer(atom_to_list(Arg)),
  io:format("~p! = ~p~n",
            [N,
             f(N)]).
