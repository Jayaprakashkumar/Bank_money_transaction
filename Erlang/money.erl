-module(money).
-export([start/0, printBankMessage/4, customerApproved/2, printBankAmountStatus/2, maximumValueForCustomer/2]).

printBankMessage(BankName, CustName, Response, Amount) ->
	io:fwrite("~w ~w a loan of ~w dollars from ~w ~n",[BankName, Response, Amount, CustName]).

printBankAmountStatus(BankName1, Amount1) ->
		io:fwrite("~w has ~w dollar(s) remaining ~n",[BankName1, Amount1]).

customerApproved(Name, CustMap) ->
	Value = maps:get(Name, CustMap),
	io:fwrite("~w has reached the objective of  ~w dollar(s). Woo Hoo! ~n",[Name, Value]).

maximumValueForCustomer(Name, Res) ->
	io:fwrite("~w was only able to borrow ~w dollar(s). Boo Hoo!~n",[Name, Res]).
	
bankMapCall(BankMap) ->
	io:fwrite("** Banks and financial resources **~n"),
	maps:fold(fun(Key, Val, ok) -> 
					  io:format("~p: ~p~n", [Key, Val]) end, ok, BankMap), 
	io:fwrite("~n"),
	
	MethodCall=fun(Key,Val,Acc)->
	Bank_PID = spawn(bank, bankAcc, [Key, Val]),
	register(Key, Bank_PID),
	timer:sleep(100)		   
	end,
	maps:fold(MethodCall,[],BankMap).


customerMapCall(CustomerMap, BankMap) ->
	io:fwrite("** Customers and loan objectives **~n"),
	maps:fold(fun(Key, Val, ok) -> 
					  io:format("~p: ~p~n", [Key, Val]) end, ok, CustomerMap), 
	io:fwrite("~n"),
	
	CustMethodCall=fun(Key,Val,Acc)->
	Cust_PID = spawn(customer, customerAcc, [Key, Val, BankMap, CustomerMap]),
	Cust_PID ! {self()},
	register(Key, Cust_PID),
	timer:sleep(100)
	end,
	maps:fold(CustMethodCall,[],CustomerMap).

start() ->
  	Cust = file:consult("customers.txt"),
		Customer = element(2,Cust),

%% 		io:fwrite("~w~n",[Customer]),
	Ban = file:consult("banks.txt"),
		Bank = element(2,Ban),

%% 		io:fwrite("~w~n",[Bank]),
  
  		CustomerMap = maps:from_list(Customer),
		BankMap = maps:from_list(Bank), 
	bankMapCall(BankMap),
	customerMapCall(CustomerMap, BankMap),
	timer:sleep(10000).
	
	
	
	

