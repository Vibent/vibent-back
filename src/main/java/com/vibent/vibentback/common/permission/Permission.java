package com.vibent.vibentback.common.permission;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
class Permission {
    @Id
    boolean isAuthorized;
}