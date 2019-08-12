-module(bank).
-export([bankAcc/2]).
-import(money,[printBankMessage/4, printBankAmountStatus/2]).

bankruptProcess(Rbc, Resource, T, Cus_PID, CustName) ->
	printBankMessage(Rbc, CustName, denies, T),
	printBankAmountStatus(Rbc,Resource),
	Cus_PID ! {T, Rbc, Resource},
    bankAcc(Rbc, Resource).

bankAcc(Rbc, Resource) ->	
    receive
        {T, Key, Cus_PID, CustName} ->
			timer:sleep(150),
			if
				T < Resource ->	
					printBankMessage(Rbc, CustName, approves, T),
   					Cus_PID ! {T, yes},
   					bankAcc(Rbc, Resource-T);
				true ->
					bankruptProcess(Rbc, Resource, T, Cus_PID, CustName)
			end     
    end.
		