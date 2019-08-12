-module(customer).
-export([customerAcc/4]).
-import(money,[printBankMessage/4, customerApproved/2, maximumValueForCustomer/2]).

%% customerAcc(Name, 0, BankMap, CustomerMap) ->
%% 	receive
%% 	 	{ReqLoan,Yes} ->
%% %% 			io:fwrite("~w received all the money~n", [Name]),
%% 			customerApproved(Name, CustomerMap)
%% %% 			self() ! {self()}
%% 	end;
	
customerAcc(Name, N, BankMap, CustomerMap) ->
	
	receive	
		{Id} ->
			RandomNum = rand:uniform(50),
			BankKey = maps:keys(BankMap),
			Key = lists:nth(rand:uniform(length(BankKey)),BankKey),
			printBankMessage(Name, Key, requested, RandomNum),
			whereis(Key) ! {RandomNum, Key, self(), Name},
			customerAcc(Name, N, BankMap, CustomerMap); 
	    {ReqLoan,Yes} ->
			timer:sleep(200),
			LoanAmount = rand:uniform(50),
			BankKey1 = maps:keys(BankMap),
			RandomBank = lists:nth(rand:uniform(length(BankKey1)),BankKey1),
			BalanceLoan = N - ReqLoan,
			if 
				LoanAmount < BalanceLoan ->
					whereis(RandomBank) ! {LoanAmount, RandomBank, self(), Name},
					printBankMessage(Name, RandomBank, requested, LoanAmount),
					customerAcc(Name, N - ReqLoan, BankMap, CustomerMap);
					
				true ->
					if
						BalanceLoan == 0 ->
							customerApproved(Name, CustomerMap);
						true ->
							printBankMessage(Name, RandomBank, requested, BalanceLoan),
							whereis(RandomBank) ! {BalanceLoan, RandomBank, self(), Name},
							customerAcc(Name, BalanceLoan , BankMap, CustomerMap)
					end
			end;
		{T, Bank, Amount} ->
			RemoveMap = maps:remove(Bank, BankMap),
			Size = maps:size(RemoveMap),	
			if
				size > 0 ->
					RandomNum = rand:uniform(50),
					BankKey = maps:keys(RemoveMap),
					Key = lists:nth(rand:uniform(length(BankKey)),BankKey),
					printBankMessage(Name, Key, requested, RandomNum),
					whereis(Key) ! {RandomNum, Key, self(), Name},
					customerAcc(Name, N, RemoveMap, CustomerMap);
				true ->
					maximumValueForCustomer(Name, N),
					customerAcc(Name, N, RemoveMap, CustomerMap)
			end		
	end.