package org.example.dienluc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "TAIKHOAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TAIKHOAN")
    private String username;
    @Column(name = "MATKHAU")
    private String password;
    @Column(name = "VOHIEUHOA")
    private Boolean disabled;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDQUYEN")
    private Role role;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Employee> employees;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Client> clients;
    @PrePersist
    protected void onCreate() {
        disabled = false;
    }
}
