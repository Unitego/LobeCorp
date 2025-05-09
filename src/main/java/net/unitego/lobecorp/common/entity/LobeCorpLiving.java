package net.unitego.lobecorp.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

//脑叶公司生物
public class LobeCorpLiving extends PathfinderMob {
    protected LobeCorpLiving(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }
}
