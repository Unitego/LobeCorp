package net.unitego.lobecorp.common.access;

import net.unitego.lobecorp.common.manager.StaffManager;

//装备要求接口
public interface EquipRequireAccess {
    StaffManager.EquipRequire getEquipRequire();//获取职员等级要求
}
