package controller;

import controller.Validate;
import model.History;
import model.Worker;
import menu.MenuView;
import menu.MenuModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Scanner;

public class Manager {

    private Validate validator = new Validate();
    private  MenuModel model;
    private  MenuView view;
    ArrayList<Worker> lw;
    ArrayList<History> lh;

   public Manager(MenuModel model, MenuView view) {
    this.model = model;
    this.view = view;
    this.lw = new ArrayList<>(); 
    this.lh = new ArrayList<>(); 
}

    
    public void startMenu() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            view.displayMenu();
            int choice = view.getUserChoice();

            switch (choice) {
                case 1:
                    addWorker(lw);
                    break;
                case 2:
                    changeSalary(lw, lh, 1);
                    break;
                case 3:
                    changeSalary(lw, lh, 2);
                    break;
                case 4:
                    printListHistory(lh);
                    break;
                case 5:
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void addWorker(ArrayList<Worker> lw) {
        System.out.println("------------------- Add Worker ---------------------");
        System.out.print("Enter code: ");
        String id = validator.checkInputString();

        System.out.print("Enter name: ");
        String name = validator.checkInputString();
        System.out.print("Enter age: ");
        int age = validator.checkInputIntLimit(18, 50);
        System.out.print("Enter salary: ");
        int salary = validator.checkInputSalary();
        System.out.print("Enter work location: ");
        String workLocation = validator.checkInputString();
        if (!validator.checkWorkerExist(lw, id, name, age, salary, workLocation)) {
            System.err.println("Duplicate.");
        } else {
            lw.add(new Worker(id, name, age, salary, workLocation));
            System.err.println("Add success.");
        }
    }

    public void changeSalary(ArrayList<Worker> lw, ArrayList<History> lh, int status) {
        if (lw.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.println("------------ Up/Down Salary ---------------");
        System.out.print("Enter code: ");
        String id = validator.checkInputString();
        Worker worker = getWorkerByCode(lw, id);
        if (worker == null) {
            System.err.println("Not exist worker.");
        } else {
            int salaryCurrent = worker.getSalary();
            int salaryUpdate;
            if (status == 1) {
                System.out.print("Enter salary: ");
                while (true) {
                    salaryUpdate = validator.checkInputSalary();
                    if (salaryUpdate <= salaryCurrent) {
                        System.err.println("Must be greater than current salary.");
                        System.out.print("Enter again: ");
                    } else {
                        break;
                    }
                }
                lh.add(new History("UP", getCurrentDate(), worker.getId(), worker.getName(),
                        worker.getAge(), salaryUpdate, worker.getWorkLocation()));
            } else {
                while (true) {
                    salaryUpdate = validator.checkInputSalary();
                    if (salaryUpdate >= salaryCurrent) {
                        System.err.println("Must be smaller than current salary.");
                        System.out.print("Enter again: ");
                    } else {
                        break;
                    }
                }
                lh.add(new History("DOWN", getCurrentDate(), worker.getId(), worker.getName(),
                        worker.getAge(), salaryUpdate, worker.getWorkLocation()));
            }
            worker.setSalary(salaryUpdate);
            System.err.println("Update success");
        }
    }

    public void printListHistory(ArrayList<History> lh) {
        if (lh.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        Collections.sort(lh);
        for (History history : lh) {
            printHistory(history);
        }
    }

    public Worker getWorkerByCode(ArrayList<Worker> lw, String id) {
        for (Worker worker : lw) {
            if (id.equalsIgnoreCase(worker.getId())) {
                return worker;
            }
        }
        return null;
    }

    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    public void printHistory(History history) {
        System.out.println("--------------------------Display Information Salary------------------------------------");
        System.out.printf("%-5s %-30s %-10s %-10s %-6s %-20s\n",
                "Code", "Name", "Age", "Salary", "Status", "Date");
        String id = history.getId().length() < 5 ? history.getId() : history.getId().substring(0, 5);
        String name = history.getName().length() < 15 ? history.getName() : history.getName().substring(0, 15);
        String status = history.getStatus().length() < 6 ? history.getStatus() : history.getStatus().substring(0, 6);

        System.out.printf("W %-5s %-30s %-9d %-10d %-6s %-20s\n",
                id, name, history.getAge(), history.getSalary(),
                status, history.getDate());
    }

}
