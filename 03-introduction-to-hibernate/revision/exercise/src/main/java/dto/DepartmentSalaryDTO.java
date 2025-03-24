package dto;

import java.math.BigDecimal;

public class DepartmentSalaryDTO {

    private String departmentName;
    private BigDecimal salary;

    public DepartmentSalaryDTO(String departmentName, BigDecimal salary) {
        this.departmentName = departmentName;
        this.salary = salary;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", this.departmentName,this.salary);
    }
}
