package com.vibent.vibentback.common.permission;

import com.vibent.vibentback.user.User;

public interface Permissible {
    boolean canRead(User user);
    boolean canWrite(User user);
    boolean canWriteChildren(User user);
}
