package model;

public record ContactData(
        String firstname,
        String middlename,
        String lastname,
        String nickname,
        String title,
        String company,
        String address,
        String home,
        String mobile,
        String work,
        String fax,
        String email,
        String email2,
        String email3,
        String homepage,
        String bday,
        String bmonth,
        String byear,
        String aday,
        String amonth,
        String ayear,
        String newGroup
) {
    public ContactData() {
        this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "");
    }

    public ContactData withFirstname(String firstname) {
        return new ContactData(firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withMiddlename(String middlename) {
        return new ContactData(this.firstname, middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withLastname(String lastname) {
        return new ContactData(this.firstname, this.middlename, lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withNickname(String nickname) {
        return new ContactData(this.firstname, this.middlename, this.lastname, nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withTitle(String title) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withCompany(String company) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withAddress(String address) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withHome(String home) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withMobile(String mobile) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withWork(String work) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withFax(String fax) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withEmail(String email) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withEmail2(String email2) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withEmail3(String email3) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withHomepage(String homepage) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }

    public ContactData withBday(String bday) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withBmonth(String bmonth) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, bmonth, this.byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withByear(String byear) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, byear, this.aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withAday(String aday) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, aday, this.amonth, this.ayear, this.newGroup);
    }
    public ContactData withAmonth(String amonth) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, amonth, this.ayear, this.newGroup);
    }
    public ContactData withAyear(String ayear) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, ayear, this.newGroup);
    }
    public ContactData withNewGroup(String newGroup) {
        return new ContactData(this.firstname, this.middlename, this.lastname, this.nickname,
                this.title, this.company, this.address, this.home, this.mobile,
                this.work, this.fax, this.email, this.email2, this.email3, this.homepage,
                this.bday, this.bmonth, this.byear, this.aday, this.amonth, this.ayear, newGroup);
    }
}