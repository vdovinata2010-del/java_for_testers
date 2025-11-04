package manager.hbm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "addressbook")

public class ContactRecord {
    @Id
    public int id;
    public String firstname;
    public String middlename;
    public String lastname;
    public String nickname;
    public String title;
    public String company;
    public String address;
    public String home;
    public String mobile;
    public String work;
    public String fax;
    public String email;
    public String email2;
    public String email3;
    public String homepage;
    public String bday;
    public String bmonth;
    public String byear;
    public String aday;
    public String amonth;
    public String ayear;

    @ManyToMany(mappedBy = "contacts")
    public List<GroupRecord> groups;

    public ContactRecord() {
    }

    public ContactRecord(int id, String firstname, String middlename, String lastname, String nickname,
                         String title, String company, String address, String home, String mobile,
                         String work, String fax, String email, String email2, String email3, String homepage) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.nickname = nickname;
        this.title = title;
        this.company = company;
        this.address = address;
        this.home = home;
        this.mobile = mobile;
        this.work = work;
        this.fax = fax;
        this.email = email;
        this.email2 = email2;
        this.email3 = email3;
        this.homepage = homepage;
    }
}
