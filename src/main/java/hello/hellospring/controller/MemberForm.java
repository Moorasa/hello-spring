package hello.hellospring.controller;

public class MemberForm {
    private String name, Uid;

    public String getName() {
        return name;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String Uid) {
        this.Uid = Uid;
    }

    public void setName(String name) {
        this.name = name;
    }
}
