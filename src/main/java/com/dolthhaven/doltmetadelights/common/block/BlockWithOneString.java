package com.dolthhaven.doltmetadelights.common.block;

import net.minecraft.world.level.block.Block;

public class BlockWithOneString extends Block {
    private final String theString;

    public BlockWithOneString(String string, Properties properties) {
        super(properties);
        this.theString = string;
    }

    public String getTheString() {
        return theString;
    }
}
