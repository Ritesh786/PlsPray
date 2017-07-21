package plspray.infoservices.lue.plspray.databind;

/**
 * Created by lue on 06-07-2017.
 */

public class ContactList {

    private String name="";
    private String number="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id="";

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl="";

    public ContactList(String name,String number,String imageUrl,String id)
    {
        this.name=name;
        this.number=number;
        this.imageUrl=imageUrl;
        this.id=id;
    }

    public ContactList() {

    }
}
