package com.dryhome.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "root")
public class CustomerView {
    private String id;
    private String name;
    //TODO could replace this with another list possibly rather than setting by reflection etc
    /**
     *  Address lines, top padded with empty strings (for letters)
     */
    private String fullNameAddressLine1;
    private String fullNameAddressLine2;
    private String fullNameAddressLine3;
    private String fullNameAddressLine4;
    private String fullNameAddressLine5;
    private String fullNameAddressLine6;
    private String fullNameAddressLine7;
    /**
     * list of address lines (currently for invoices)
     */
    private List<String> fullAddressLines = new ArrayList<>();
    private String currentDateTime;
    private String contact;
    private String tel;
    private String mob;
    private String paid;
    private String prod;
    private String notes;
    /**
     * full address, separated by newline (currently for summary record)
     */
    private String fullAddress;
    /**
     * Contact, name and address, separated by newline (used for labels)
     */
    private String fullNameAddress;

    public String getFullNameAddressLine1() {
        return fullNameAddressLine1;
    }

    public void setFullNameAddressLine1(String fullNameAddressLine1) {
        this.fullNameAddressLine1 = fullNameAddressLine1;
    }

    public String getFullNameAddressLine2() {
        return fullNameAddressLine2;
    }

    public void setFullNameAddressLine2(String fullNameAddressLine2) {
        this.fullNameAddressLine2 = fullNameAddressLine2;
    }

    public String getFullNameAddressLine3() {
        return fullNameAddressLine3;
    }

    public void setFullNameAddressLine3(String fullNameAddressLine3) {
        this.fullNameAddressLine3 = fullNameAddressLine3;
    }

    public String getFullNameAddressLine4() {
        return fullNameAddressLine4;
    }

    public void setFullNameAddressLine4(String fullNameAddressLine4) {
        this.fullNameAddressLine4 = fullNameAddressLine4;
    }

    public String getFullNameAddressLine5() {
        return fullNameAddressLine5;
    }

    public void setFullNameAddressLine5(String fullNameAddressLine5) {
        this.fullNameAddressLine5 = fullNameAddressLine5;
    }

    public String getFullNameAddressLine6() {
        return fullNameAddressLine6;
    }

    public void setFullNameAddressLine6(String fullNameAddressLine6) {
        this.fullNameAddressLine6 = fullNameAddressLine6;
    }

    public String getFullNameAddressLine7() {
        return fullNameAddressLine7;
    }

    public void setFullNameAddressLine7(String fullNameAddressLine7) {
        this.fullNameAddressLine7 = fullNameAddressLine7;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getFullNameAddress() {
        return fullNameAddress;
    }

    public void setFullNameAddress(String fullNameAddress) {
        this.fullNameAddress = fullNameAddress;
    }

    public List<String> getFullAddressLines() {
        return fullAddressLines;
    }

    public void setFullAddressLines(List<String> fullAddressLines) {
        this.fullAddressLines = fullAddressLines;
    }

    @Override
    public String toString() {
        return "CustomerView{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", fullNameAddressLine1='" + fullNameAddressLine1 + '\'' +
            ", fullNameAddressLine2='" + fullNameAddressLine2 + '\'' +
            ", fullNameAddressLine3='" + fullNameAddressLine3 + '\'' +
            ", fullNameAddressLine4='" + fullNameAddressLine4 + '\'' +
            ", fullNameAddressLine5='" + fullNameAddressLine5 + '\'' +
            ", fullNameAddressLine6='" + fullNameAddressLine6 + '\'' +
            ", fullNameAddressLine7='" + fullNameAddressLine7 + '\'' +
            ", fullAddressLines=" + fullAddressLines +
            ", currentDateTime='" + currentDateTime + '\'' +
            ", contact='" + contact + '\'' +
            ", tel='" + tel + '\'' +
            ", mob='" + mob + '\'' +
            ", paid='" + paid + '\'' +
            ", prod='" + prod + '\'' +
            ", notes='" + notes + '\'' +
            ", fullAddress='" + fullAddress + '\'' +
            ", fullNameAddress='" + fullNameAddress + '\'' +
            '}';
    }
}
