package springframework;

import org.springframework.beans.MutablePropertyValues;

public class PropertyValue {
    private final String name;
    private final Object value;
    public PropertyValue(String name,Object value){
        this.name=name;
        this.value=value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PropertyValue{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

}
