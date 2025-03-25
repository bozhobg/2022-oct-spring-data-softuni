package helpers;

import java.math.BigDecimal;

public class DepartmentMaxSalary {
    private Integer empId;
    private String departmentName;
    private BigDecimal maxSalary;

    public DepartmentMaxSalary(String departmentName, BigDecimal maxSalary) {
        this.departmentName = departmentName;
        this.maxSalary = maxSalary;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }
}
