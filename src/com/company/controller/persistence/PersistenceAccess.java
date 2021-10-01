package com.company.controller.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceAccess {

    private String VALID_ACCOUNTS_LOCATION = System.getProperty("user.home") + File.separator + "validAccounts.ser";
    private EnCoder enCoder = new EnCoder();

    public List<ValidAccount> loadFromPersistence(){
        List<ValidAccount> resultList = new ArrayList<ValidAccount>();
        try{
            FileInputStream fileInputStream = new FileInputStream(VALID_ACCOUNTS_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<ValidAccount> persistedList = (List<ValidAccount>) objectInputStream.readObject();
            decodePassword(persistedList);
            resultList.addAll(persistedList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }

    private void decodePassword(List<ValidAccount> persistedList) {
        for(ValidAccount validAccount : persistedList){
            String originalPassword = validAccount.getPassword();
            validAccount.setPassword(enCoder.decode(originalPassword));
        }
    }

    public void saveToPersistence(List<ValidAccount> validAccounts){
        try{
            File file = new File(VALID_ACCOUNTS_LOCATION);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            encodePassword(validAccounts);
            objectOutputStream.writeObject(validAccounts);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void encodePassword(List<ValidAccount> persistedList) {
        for(ValidAccount validAccount : persistedList){
            String originalPassword = validAccount.getPassword();
            validAccount.setPassword(enCoder.encode(originalPassword));
        }
    }
}
