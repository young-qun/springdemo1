package young.demo.base;

import com.sun.istack.internal.NotNull;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    private  Long  id;

    private  String  name;

    private Integer age;

    private Boolean  sex;

    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
}
