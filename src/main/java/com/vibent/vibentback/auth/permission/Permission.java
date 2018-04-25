package com.vibent.vibentback.auth.permission;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
class Permission {
    @Id
    boolean isAuthorized;
}