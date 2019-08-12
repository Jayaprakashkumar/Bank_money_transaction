//package com.course.example;
//
//import com.course.example.models.Bank;
//import com.course.example.models.Customer;
//import com.course.example.worker.LoanProcessor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Money Lending App using multiple threads!
 *
 */
public class money
{
//    private static final Logger System.out.printf = System.out.printfFactory.getSystem.out.printf(Money.class);
    public static void main( String[] args )
    {
    	final String rootPath = "";
        final String custFileName = "customers.txt";
        final String bankFileName = "banks.txt";
        
        List<Customer> customerList = new ArrayList<>();
        List<Bank> bankList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(rootPath + custFileName))) {
            customerList = stream.filter(line -> !"".equals(line.trim())).map(s -> {
                final String[] tokens = s.replaceAll("\\{", "").replaceAll("}", "").replaceAll("\\.", "").split(",");
                return  new Customer(tokens[0], tokens[1]);
            }).collect(Collectors.toList());
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.printf("** Customers and loan objectives **\n");
        customerList.forEach(e -> System.out.println(e));
        System.out.println();
        

        try (Stream<String> stream = Files.lines(Paths.get(rootPath + bankFileName))) {
            bankList = stream.filter(line -> !"".equals(line.trim())).map(s -> {
                final String[] tokens = s.replaceAll("\\{", "").replaceAll("}", "").replaceAll("\\.", "").split(",");
                return  new Bank(tokens[0], tokens[1]);
            }).collect(Collectors.toList());
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        System.out.printf("** Banks and financial resources **\n");
        bankList.forEach(e -> System.out.println(e));
        System.out.println();
        
        ExecutorService executorService = new ThreadPoolExecutor(customerList.size(), customerList.size() + bankList.size(), 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        Runtime.getRuntime().addShutdownHook(new ShutdownHook(executorService));

        Random rand = new Random();
        for (int k=0; k< 20; k++) {
            for (int i = 0; i < customerList.size(); i++) {
                final Customer customer = customerList.get(i);

                if (customer.isLoanObjectiveMet()) {
                    continue;
                }

                final Bank bank = bankList.get(rand.nextInt(bankList.size()));

                if (customer.getRejectedBanks().contains(bank.getName())) {
                    continue;
                }

                final Long dueLoan = customer.getLoanObjective().get() - customer.getLoanAmount().get();
                final Integer loanAmount = (int) (Math.random() * Math.min(dueLoan, 50)) + 1;
                LoanProcessor loanProcessor = new LoanProcessor(customer, bank, loanAmount);
                executorService.execute(loanProcessor);
                System.out.println(customer.getName() + " requests a loan of "+ loanAmount + " dollar(s) from "+ bank.getName());
                
            }

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bankList.forEach(e -> System.out.println(e.displayString()));

        customerList.forEach(e -> System.out.println(e.displayString()));

    }
}
