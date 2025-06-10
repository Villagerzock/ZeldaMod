package net.villagerzock.projektarbeit.iMixins;

import net.minecraft.util.math.Vec3d;

public interface ICamera {
    void setPos(Vec3d vec3d);
    void setFollowPlayer(boolean b);
    boolean isFollowingPlayer();
}
