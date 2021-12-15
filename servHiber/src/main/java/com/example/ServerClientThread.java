package com.example;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ServerClientThread {
    private Socket clientDialog;
    InputStream input = null;
    OutputStream output = null;
    public int func = 0;
    private static SessionFactory sessionFactory;

    ServerClientThread(Socket inSocket) {
        this.clientDialog = inSocket;
    }
    private Amortization createAmortization(Capital temp)
    {
        return new Amortization(1, 1.0/((temp.getUsefullLife() * 12.0)),
                1.0/(temp.getUsefullLife()),
                temp.getCapitalPrice()/(temp.getUsefullLife() * 12.0),
                temp.getCapitalPrice()/(temp.getUsefullLife()), temp.getCapitalId());
    }
    public void run() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            IDatabaseDriver dbDriver = new DatabaseDriver(sessionFactory);

            this.input = this.clientDialog.getInputStream();
            this.output = this.clientDialog.getOutputStream();
            boolean flag = true;

            while(flag) {
                byte[] buf = new byte[1024];
                this.input.read(buf);
                System.out.println(buf);
                String req = new String(buf, StandardCharsets.UTF_8);
                String[] reqq = req.split(" ");
                System.out.println("Answer");
                System.out.println(req + "\n");
                if (req.equalsIgnoreCase("quit")) {
                    System.out.println("Client initialize connection suicide.......");
                    this.output.write("OK".getBytes());
                    break;
                }

                func = Integer.parseInt(reqq[0]);
                String resp = null;
                //логин
                if (func == 1) {
                    var criteria = new HashMap<String, Object>();
                    criteria.put("login", reqq[1]);
                    criteria.put("password", reqq[2]);

                    if (!dbDriver.getByCriteria(Users.class, criteria).isEmpty()) {
                       if(dbDriver.getByCriteria(Users.class, criteria).get(0).getAccess() == 1)resp = "1 "; else
                           resp = "3 ";
                    } else {
                        resp = "2 ";
                    }
                }
                //регистрация
                else if (func == 2) {
                    var criteria = new HashMap<String, Object>();
                    criteria.put("login", reqq[1]);
                    if (dbDriver.getByCriteria(Users.class, criteria).isEmpty()) {
                        dbDriver.create(new Users(1, reqq[1], reqq[2], 2, 1));
                        resp = "1 ";
                    } else {
                        resp = "2 ";
                    }
                }
                //переача данных для заполнения таблицы основных фондов
                else if (func == 3) {
                    var temp = dbDriver.getByCriteria(Capital.class, new HashMap<String, Object>());
                    resp = "3 " + "\n" + temp.size() + "\n";
                    for (int i = 0; i < temp.size(); i++){
                        resp += temp.get(i).getCapitalName() + "\n" + temp.get(i).getCapitalPrice() + "\n" +
                                temp.get(i).getUsefullLife() + "\n";
                    }
                }
                //добавление в БД запись основных фондов
                else if (func == 4) {
                    var temp = new Capital(1, reqq[1], Double.parseDouble(reqq[2]), Integer.parseInt(reqq[3]));
                    var criteria = new HashMap<String, Object>();
                    criteria.put("capitalName", reqq[1]);
                    criteria.put("capitalPrice", Double.parseDouble(reqq[2]));
                    criteria.put("usefullLife", Integer.parseInt(reqq[3]));
                    dbDriver.create(temp);
                    var tempCapital = dbDriver.getByCriteria(Capital.class, criteria).get(0);
                    var tempAmort = createAmortization(tempCapital);
                    dbDriver.create(tempAmort);
                    resp = "1 ";
                }
                //удаление записи БД основных фондов
                else if (func == 5) {
                    var a = new HashMap<String, Object>();
                    reqq = req.split("\n");
                    a.put("capitalName", reqq[1]);
                    a.put("capitalPrice", Double.parseDouble(reqq[2]));
                    a.put("usefullLife", Integer.parseInt(reqq[3]));


                    var temp = dbDriver.getByCriteria(Capital.class, a);
                    if (!temp.isEmpty()) {
                        var crit = new HashMap<String, Object>();
                        crit.put("capital", temp.get(0).getCapitalId());
                        dbDriver.delete(dbDriver.getByCriteria(Amortization.class, crit).get(0));
                        dbDriver.delete(temp.get(0));
                        resp = "1 ";
                    } else {
                        resp = "2 ";
                    }
                }
                //изменение записи БД основных фондов
                else if (func == 6) {
                    var a = new HashMap<String, Object>();
                    reqq = req.split("\n");
                    a.put("capitalName", reqq[1]);
                    a.put("capitalPrice", Double.parseDouble(reqq[2]));
                    a.put("usefullLife", Integer.parseInt(reqq[3]));

                    var temp = dbDriver.getByCriteria(Capital.class, a);

                    if (!temp.isEmpty()) {
                        temp.get(0).setCapitalName(reqq[4]);
                        temp.get(0).setCapitalPrice(Double.parseDouble(reqq[5]));
                        temp.get(0).setUsefullLife(Integer.parseInt(reqq[6]));
                        dbDriver.update(temp.get(0));
                        var tempAmort = createAmortization(temp.get(0));
                        var crit = new HashMap<String, Object>();
                        crit.put("capital", temp.get(0).getCapitalId());
                        tempAmort.setAmortizationId(dbDriver.getByCriteria(Amortization.class, crit).get(0).getAmortizationId());
                        dbDriver.update(tempAmort);
                        resp = "1 ";
                    } else {
                        resp = "2 ";
                    }
                }
                //передача данных для заполнения таблицы амортизации
                else if (func == 7) {
                    var temp = dbDriver.getByCriteria(Amortization.class, new HashMap<String, Object>());
                    resp = "7 " + "\n" + temp.size() + "\n";
                    for (int i = 0; i < temp.size(); i++){
                        resp +=  temp.get(i).getMonthPerAmort()
                        + "\n" + temp.get(i).getYearPerAmort() + "\n" + temp.get(i).getMonthCostAmort()
                        + "\n" + temp.get(i).getYearCostAmort() + "\n" + dbDriver.read(Capital.class, temp.get(i).getCapital()).getCapitalName() + "\n";
                    }
                    var aa = 0;

                }
                //передача данных для заполнения таблицы пользователей
                else if (func == 8) {
                    var temp = dbDriver.getByCriteria(Users.class, new HashMap<String, Object>());
                    resp = "8 " + "\n" + temp.size() + "\n";
                    for (int i = 0; i < temp.size(); i++){
                        resp +=  temp.get(i).getLogin() + "\n" + temp.get(i).getPassword() + "\n" +
                                dbDriver.read(Access.class, temp.get(i).getAccess()).getType() + "\n"  +
                                dbDriver.read(Status.class, temp.get(i).getStatus()).getStatus()+ "\n";
                    }
                    var aa = 0;
                }
                //добавление нового пользователя
                else if (func == 9) {
                    var criteria = new HashMap<String, Object>();

                    criteria.put("login", reqq[1]);
                    var a = new HashMap<String, Object>();
                    a.put("type", reqq[3]);
                    var temp = dbDriver.getByCriteria(Access.class, a);
                    if (dbDriver.getByCriteria(Users.class, criteria).isEmpty()) {
                        dbDriver.create(new Users(1, reqq[1], reqq[2],
                                temp.get(0).getAccessId(), 1));
                        resp = "1 ";
                    } else {
                        resp = "2 ";
                    }
                }
                //удаление пользователя
                else if (func == 10) {
                    var a = new HashMap<String, Object>();
                    reqq = req.split("\n");
                    a.put("login", reqq[1]);
                    a.put("password", reqq[2]);

                    var temp = dbDriver.getByCriteria(Users.class, a);
                    if (!temp.isEmpty()) {
                        dbDriver.delete(temp.get(0));
                        resp = "1 ";
                    } else {
                        resp = "2 ";
                    }
                }
                //смена статуса пользоваетеля
                else if (func == 11) {
                    reqq = req.split("\n");
                    var criteria = new HashMap<String, Object>();
                    criteria.put("login", reqq[1]);
                    var a = new HashMap<String, Object>();

                    var temp = dbDriver.getByCriteria(Users.class, criteria);
                    if (!temp.isEmpty()) {
                        if (temp.get(0).getStatus()== 1)
                            temp.get(0).setStatus(2);
                        else
                            temp.get(0).setStatus(1);
                        dbDriver.update(temp.get(0));
                        resp = "1 ";
                    } else {
                        resp = "2 ";
                    }
                }
                System.out.println(resp);
                this.output.write(resp.getBytes());
            }

            System.out.println("Connection - DONE!");
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            try {
                this.input.close();
                this.output.close();
                this.clientDialog.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

    }
}

