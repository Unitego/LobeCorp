package net.unitego.lobecorp.common.entity.abnormality.tool;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.unitego.lobecorp.common.entity.abnormality.Abnormality;

public class ToolAbnormality extends Abnormality {
    protected ToolAbnormality(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }
}
