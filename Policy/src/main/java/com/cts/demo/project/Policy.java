package com.cts.demo.project;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
//Table Name
@Table(name = "policy_info")
//Generate Getter and Setter
@Data
//Constructor with All Feilds
@AllArgsConstructor
//no Argument Constructor
@NoArgsConstructor
public class Policy {
   @Id
   @Positive(message = "Make Sure the Value is Positive")
   private long policyId;
   @NotEmpty(message = "Make Sure the Name is Not Empty")
   private String policyName;
   @Min(value = 50000, message = "Make Sure the Amount in Greater than 50000")
   private double premiumAmount;
   @NotEmpty(message = "Make Sure it is Not Empty")
   private String coverageDetails;
   //Validity Period of Insurance
   private LocalDate validityPeriod;
}