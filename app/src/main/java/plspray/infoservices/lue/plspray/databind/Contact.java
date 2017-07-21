package plspray.infoservices.lue.plspray.databind;

/**
 * Created by lue on 06-07-2017.
 */

public class Contact {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String name="";
    private String number="";

    public Contact(String name,String number)
    {
        this.name=name;
        this.number=number;
    }

    public Contact(){}

}
