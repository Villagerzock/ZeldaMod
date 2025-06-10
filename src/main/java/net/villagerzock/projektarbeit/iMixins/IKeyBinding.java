package net.villagerzock.projektarbeit.iMixins;

import net.minecraft.client.util.InputUtil;

public interface IKeyBinding {
    InputUtil.Key getBoundKey();
}
