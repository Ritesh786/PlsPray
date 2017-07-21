package plspray.infoservices.lue.plspray.databind;

/**
 * Created by lue on 18-07-2017.
 */

public class PhoneNumber {
    private boolean isSelected;
    private String phonenu;
    private String getno;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGetno() {
        return getno;
    }

    public void setGetno(String getno) {
        this.getno = getno;
    }

    public PhoneNumber() {
    }

    public String getPhonenu() {
        return phonenu;
    }

    public void setPhonenu(String phonenu) {
        this.phonenu = phonenu;
    }

    public PhoneNumber(String phonenu) {
        this.phonenu = phonenu;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
